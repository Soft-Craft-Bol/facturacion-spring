package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.MensajeRecepcion;
import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.RespuestaRecepcion;
import com.gaspar.facturador.application.request.ValidacionPaqueteRequest;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.application.response.PaquetesResponse;
import com.gaspar.facturador.application.response.ValidacionPaqueteResponse;
import com.gaspar.facturador.application.rest.dto.EnvioPaqueteRequest;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.DetalleCompraVenta;
import com.gaspar.facturador.domain.FacturaElectronicaCompraVenta;
import com.gaspar.facturador.domain.helpers.Utils;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.IEventoSignificativoRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.emision.EnvioFacturaService;
import com.gaspar.facturador.domain.service.emision.GeneraFacturaService;
import com.gaspar.facturador.persistence.FacturaRepository;
import com.gaspar.facturador.persistence.dto.FacturaDetalleDTO;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.utils.FacturaCompressor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.gaspar.facturador.persistence.dto.FacturaDTO;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;


@Service
public class FacturacionService {

    private final AppConfig appConfig;
    private final GeneraFacturaService generaFacturaService;
    private final EnvioFacturaService envioFacturaService;
    private final EnvioPaquetesService envioPaquetesService;
    private final AnulacionFacturaService anulacionFacturaService;
    private final ReversionFacturaService reversionFacturaService;
    private final FacturaRepository facturaRepository;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final IEventoSignificativoRepository eventoRepository;
    private final VerificacionPaqueteService verificacionPaqueteService;
    private final VentaService ventaService;
    private final CufdService cufdService;
    private final FacturaCompressor facturaCompressor;


    public FacturacionService(
            AppConfig appConfig, GeneraFacturaService generaFacturaService,
            EnvioFacturaService envioFacturaService, EnvioPaquetesService envioPaquetesService, AnulacionFacturaService anulacionFacturaService, ReversionFacturaService reversionFacturaService, FacturaRepository facturaRepository,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository, IEventoSignificativoRepository eventoRepository, VerificacionPaqueteService verificacionPaqueteService, VentaService ventaService, CufdService cufdService, FacturaCompressor facturaCompressor
    ) {
        this.appConfig = appConfig;
        this.generaFacturaService = generaFacturaService;
        this.envioFacturaService = envioFacturaService;
        this.envioPaquetesService = envioPaquetesService;
        this.anulacionFacturaService = anulacionFacturaService;
        this.reversionFacturaService = reversionFacturaService;
        this.facturaRepository = facturaRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
        this.eventoRepository = eventoRepository;
        this.verificacionPaqueteService = verificacionPaqueteService;
        this.ventaService = ventaService;
        this.cufdService = cufdService;
        this.facturaCompressor = facturaCompressor;
    }

    @Transactional(rollbackFor = Exception.class)
    public FacturaResponse emitirFactura(VentaRequest ventaRequest) throws Exception {
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta())
                .orElseThrow(() -> new ProcessException("Punto venta no encontrado"));

        CufdEntity cufd = obtenerCufdVigente(puntoVenta);

        FacturaElectronicaCompraVenta factura = generaFacturaService.llenarDatos(ventaRequest, cufd);

        String xmlContent = new String(generaFacturaService.getXmlBytes(factura));

        try {
            byte[] xmlComprimidoZip = generaFacturaService.obtenerArchivo(factura, false);
            RespuestaRecepcion respuesta = envioFacturaService.enviar(puntoVenta, cufd, xmlComprimidoZip);

            if (respuesta.getCodigoEstado() != 908) {
                String mensajeError = "Error al enviar factura al SIAT: " +
                        respuesta.getCodigoEstado() + " - " +
                        respuesta.getMensajesList().stream()
                                .map(MensajeRecepcion::getDescripcion)
                                .collect(Collectors.joining(", "));
                throw new ProcessException(mensajeError);
            }

            FacturaEntity facturaEntity = persistirFactura(factura);

            registrarVenta(ventaRequest, facturaEntity.getId());

            return construirRespuestaExitosa(factura, xmlContent, respuesta);

        } catch (Exception e) {
            String mensajeError = "Error al enviar factura al SIAT: " + e.getMessage();
            throw new ProcessException(mensajeError);
        } finally {
            generaFacturaService.limpiarFacturasTemporales();
        }
    }

    private CufdEntity obtenerCufdVigente(PuntoVentaEntity puntoVenta) throws Exception {
        Optional<CufdEntity> cufdOpt = cufdRepository.findActual(puntoVenta);

        if (cufdOpt.isEmpty() || cufdOpt.get().getFechaVigencia().isBefore(LocalDateTime.now())) {
            cufdService.obtenerCufd(puntoVenta.getId());
            cufdOpt = cufdRepository.findActual(puntoVenta);
        }

        return cufdOpt.orElseThrow(() -> new ProcessException("No se pudo obtener un CUFD vigente"));
    }

    private FacturaEntity persistirFactura(FacturaElectronicaCompraVenta factura) {
        FacturaEntity entity = new FacturaEntity();

        // Mapear cabecera
        entity.setNitEmisor(factura.getCabecera().getNitEmisor());
        entity.setRazonSocialEmisor(factura.getCabecera().getRazonSocialEmisor());
        entity.setMunicipio(factura.getCabecera().getMunicipio());
        entity.setTelefono(factura.getCabecera().getTelefono());
        entity.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        entity.setCuf(factura.getCabecera().getCuf());
        entity.setCufd(factura.getCabecera().getCufd());
        entity.setCodigoSucursal(factura.getCabecera().getCodigoSucursal());
        entity.setDireccion(factura.getCabecera().getDireccion());
        entity.setCodigoPuntoVenta(factura.getCabecera().getCodigoPuntoVenta());
        entity.setFechaEmision(Utils.xMLGregorianCalendarToLocalDateTime(factura.getCabecera().getFechaEmision()));
        entity.setNombreRazonSocial(factura.getCabecera().getNombreRazonSocial());
        entity.setCodigoTipoDocumentoIdentidad(factura.getCabecera().getCodigoTipoDocumentoIdentidad());
        entity.setNumeroDocumento(factura.getCabecera().getNumeroDocumento());
        entity.setComplemento(factura.getCabecera().getComplemento());
        entity.setCodigoCliente(factura.getCabecera().getCodigoCliente());
        entity.setCodigoMetodoPago(factura.getCabecera().getCodigoMetodoPago());
        entity.setNumeroTarjeta(factura.getCabecera().getNumeroTarjeta());
        entity.setMontoTotal(factura.getCabecera().getMontoTotal());
        entity.setMontoTotalSujetoIva(factura.getCabecera().getMontoTotalSujetoIva());
        entity.setMontoGiftCard(factura.getCabecera().getMontoGiftCard());
        entity.setDescuentoAdicional(factura.getCabecera().getDescuentoAdicional());
        entity.setCodigoExcepcion(factura.getCabecera().getCodigoExcepcion());
        entity.setCafc(factura.getCabecera().getCafc());
        entity.setCodigoMoneda(factura.getCabecera().getCodigoMoneda());
        entity.setTipoCambio(factura.getCabecera().getTipoCambio());
        entity.setMontoTotalMoneda(factura.getCabecera().getMontoTotalMoneda());
        entity.setLeyenda(factura.getCabecera().getLeyenda());
        entity.setUsuario(factura.getCabecera().getUsuario());
        entity.setCodigoDocumentoSector(factura.getCabecera().getCodigoDocumentoSector());
        entity.setEstado("EMITIDA");

        // Mapear detalles
        List<FacturaDetalleEntity> detalles = factura.getDetalle().stream()
                .map(detalle -> {
                    FacturaDetalleEntity detalleEntity = new FacturaDetalleEntity();
                    detalleEntity.setActividadEconomica(detalle.getActividadEconomica());
                    detalleEntity.setCodigoProductoSin(detalle.getCodigoProductoSin());
                    detalleEntity.setCodigoProducto(detalle.getCodigoProducto());
                    detalleEntity.setDescripcion(detalle.getDescripcion());
                    detalleEntity.setCantidad(detalle.getCantidad());
                    detalleEntity.setUnidadMedida(detalle.getUnidadMedida());
                    detalleEntity.setPrecioUnitario(detalle.getPrecioUnitario());
                    detalleEntity.setMontoDescuento(detalle.getMontoDescuento());
                    detalleEntity.setSubTotal(detalle.getSubTotal());
                    detalleEntity.setNumeroSerie(detalle.getNumeroSerie());
                    detalleEntity.setNumeroImei(detalle.getNumeroImei());
                    detalleEntity.setFactura(entity);
                    return detalleEntity;
                })
                .collect(Collectors.toList());

        entity.setDetalleList(detalles);
        return facturaRepository.save(entity);
    }

    private void registrarVenta(VentaRequest ventaRequest, Long idFactura) {
        VentaSinFacturaRequest venta = new VentaSinFacturaRequest();
        venta.setIdPuntoVenta(ventaRequest.getIdPuntoVenta().longValue());
        venta.setIdCliente(ventaRequest.getIdCliente());
        venta.setTipoComprobante(ventaRequest.getTipoComprobante());
        venta.setMetodoPago(ventaRequest.getMetodoPago());
        venta.setUsername(ventaRequest.getUsername());
        venta.setDetalle(ventaRequest.getDetalle());
        venta.setIdfactura(idFactura);
        venta.setMontoRecibido(ventaRequest.getMontoRecibido());
        venta.setMontoDevuelto(ventaRequest.getMontoDevuelto());
        venta.setCajaId(ventaRequest.getCajasId());

        ventaService.saveVenta(venta);
    }

    private FacturaResponse construirRespuestaExitosa(
            FacturaElectronicaCompraVenta factura,
            String xmlContent,
            RespuestaRecepcion respuesta) {

        FacturaResponse response = new FacturaResponse();
        response.setCodigoEstado(respuesta.getCodigoEstado());
        response.setCuf(factura.getCabecera().getCuf());
        response.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        response.setXmlContent(xmlContent);
        response.setMensaje("Factura emitida correctamente");
        return response;
    }

    public FacturaResponse emitirContingencia(VentaRequest ventaRequest) throws Exception {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) throw new ProcessException("Cufd vigente no encontrado");
        // Generar la factura
        FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventaRequest, cufd.get());
        byte[] xmlBytes = this.generaFacturaService.getXmlBytes(factura);
        String xmlContent = new String(xmlBytes);
        // Continuar con el flujo normal
        byte[] xmlComprimidoZip = this.generaFacturaService.obtenerArchivo(factura, true);
        // Construir la respuesta
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setCuf(factura.getCabecera().getCuf());
        facturaResponse.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        facturaResponse.setXmlContent(xmlContent);

        return facturaResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public PaquetesResponse enviarPaquetes(EnvioPaqueteRequest envioPaqueteRequest) throws IOException {
        try {
            // 1. Validar y obtener entidades necesarias
            PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(envioPaqueteRequest.idPuntoVenta())
                    .orElseThrow(() -> new ProcessException("Punto de venta no encontrado"));

            CufdEntity cufd = cufdRepository.findActual(puntoVenta)
                    .orElseThrow(() -> new ProcessException("CUFD vigente no encontrado"));

            // 2. Obtener el evento significativo relacionado
            EventoSignificativoEntity evento = eventoRepository.findByCodigoRecepcionEventoAndPuntoVenta(
                            envioPaqueteRequest.codigoEvento(),
                            puntoVenta)
                    .orElseThrow(() -> new ProcessException("Evento significativo no encontrado o no vigente"));

            // 3. Comprimir y enviar paquete
            byte[] xmlsComprimidosZip = facturaCompressor.comprimirPaqueteFacturas();
            int cantidadFacturas = facturaCompressor.getCantidadArchivosXML();

            RespuestaRecepcion respuestaRecepcion = envioPaquetesService.enviarPaqueteFacturas(
                    puntoVenta,
                    cufd,
                    xmlsComprimidosZip,
                    cantidadFacturas,
                    envioPaqueteRequest.codigoEvento(),
                    envioPaqueteRequest.cafc()
            );

            // 4. Crear respuesta
            PaquetesResponse paquetesResponse = new PaquetesResponse();
            paquetesResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());

            paquetesResponse.setCodigoRecepcion(respuestaRecepcion.getCodigoRecepcion());
            paquetesResponse.setCodigoEvento(envioPaqueteRequest.codigoEvento());
            paquetesResponse.setCantidadFacturasAlmacenadas(cantidadFacturas);

            // 5. Determinar si la respuesta es exitosa
            boolean envioExitoso = (respuestaRecepcion.getCodigoEstado() == 901 ||
                    respuestaRecepcion.getCodigoEstado() == 902) &&
                    respuestaRecepcion.getCodigoRecepcion() != null;

            // 6. Actualizar evento significativo si el envío fue exitoso
            if (envioExitoso) {
                evento.setCodigoRecepcionPaquete(respuestaRecepcion.getCodigoRecepcion());
                evento.setCodigoEventoPaquete(envioPaqueteRequest.codigoEvento());
                evento.setCantidadFacturas(cantidadFacturas);
                evento.setEtapa("PAQUETE_ENVIADO");
                evento.setVigente(false); // Marcamos como no vigente después del envío
                eventoRepository.save(evento);
            }

            // 7. Personalizar mensajes según el código de estado
            String mensajeBase;
            if (respuestaRecepcion.getCodigoEstado() == 901) {
                mensajeBase = "Paquete recibido correctamente por el SIAT. Estado: PENDIENTE DE PROCESAMIENTO";
            } else if (respuestaRecepcion.getCodigoEstado() == 902) {
                mensajeBase = "Paquete recibido con observaciones. Estado: PENDIENTE DE VALIDACIÓN";
            } else if (respuestaRecepcion.getCodigoEstado() == 903) {
                mensajeBase = "Paquete rechazado por el SIAT";
            } else {
                mensajeBase = "Estado del paquete desconocido";
            }
            paquetesResponse.setMensaje(mensajeBase);

            // 8. Agregar mensajes adicionales del SIAT si existen
            if (respuestaRecepcion.getMensajesList() != null && !respuestaRecepcion.getMensajesList().isEmpty()) {
                StringBuilder mensajeDetallado = new StringBuilder(mensajeBase);
                mensajeDetallado.append(" Detalles: ");
                for (MensajeRecepcion mensaje : respuestaRecepcion.getMensajesList()) {
                    mensajeDetallado.append(mensaje.getDescripcion()).append(". ");
                }
                paquetesResponse.setMensaje(mensajeDetallado.toString());
            }

            // 9. Limpieza de archivos temporales
            this.generaFacturaService.limpiarFacturasTemporales();
            if (envioExitoso) {
                limpiarArchivosTemporales();
            }

            return paquetesResponse;
        } finally {
            limpiarArchivosPorTiempo();
        }
    }

    /**
     * Limpia archivos temporales de paquetes cuando el envío fue exitoso
     */
    private void limpiarArchivosTemporales() {
        String directorioPaquetes = appConfig.getPathFiles() + "/facturas/paquetes/";
        File directorio = new File(directorioPaquetes);

        if (directorio.exists() && directorio.isDirectory()) {
            File[] archivos = directorio.listFiles((dir, name) ->
                    name.endsWith(".xml") || name.endsWith(".zip"));

            if (archivos != null) {
                for (File archivo : archivos) {
                    try {
                        if (archivo.delete()) {
                            System.out.println("Archivo eliminado exitosamente: " + archivo.getName());
                        } else {
                            System.err.println("No se pudo eliminar el archivo: " + archivo.getName());
                        }
                    } catch (Exception e) {
                        System.err.println("Error al eliminar archivo temporal: " +
                                archivo.getName() + " - " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Limpia archivos que tengan más de 72 horas de antigüedad
     */
    private void limpiarArchivosPorTiempo() {
        String directorioPaquetes = appConfig.getPathFiles() + "/facturas/paquetes/";
        File directorio = new File(directorioPaquetes);

        if (directorio.exists() && directorio.isDirectory()) {
            File[] archivos = directorio.listFiles((dir, name) ->
                    name.endsWith(".xml") || name.endsWith(".zip"));

            if (archivos != null) {
                long tiempoLimite = System.currentTimeMillis() - (72 * 60 * 60 * 1000); // 72 horas en milisegundos

                for (File archivo : archivos) {
                    try {
                        if (archivo.lastModified() < tiempoLimite) {
                            if (archivo.delete()) {
                                System.out.println("Archivo eliminado por antigüedad (>72h): " + archivo.getName());
                            } else {
                                System.err.println("No se pudo eliminar archivo antiguo: " + archivo.getName());
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error al verificar/eliminar archivo por tiempo: " +
                                archivo.getName() + " - " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Método alternativo para usar con Scheduled Task (opcional)
     * Debe ser llamado periódicamente para limpiar archivos antiguos
     */
    @Scheduled(fixedRate = 3600000) // Ejecutar cada hora
    public void limpiezaProgramadaArchivos() {
        try {
            limpiarArchivosPorTiempo();
        } catch (Exception e) {
            System.err.println("Error en limpieza programada: " + e.getMessage());
        }
    }

    public ValidacionPaqueteResponse validarRecepcionPaquete(ValidacionPaqueteRequest request) throws RemoteException {
        try {
            // 1. Obtener punto de venta y CUFD
            PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(request.getIdPuntoVenta())
                    .orElseThrow(() -> new ProcessException("Punto de venta no encontrado con ID: " + request.getIdPuntoVenta()));

            CufdEntity cufd = cufdRepository.findActual(puntoVenta)
                    .orElseThrow(() -> new ProcessException("No se encontró un CUFD vigente para el punto de venta"));

            // 2. Buscar el evento significativo relacionado con el código de recepción
            EventoSignificativoEntity evento = eventoRepository.findByCodigoRecepcionPaqueteAndPuntoVenta(
                            request.getCodigoRecepcion(),
                            puntoVenta)
                    .orElseThrow(() -> new ProcessException("No se encontró evento significativo con código de recepción: " + request.getCodigoRecepcion()));

            // 3. Validar el paquete con SIAT
            RespuestaRecepcion respuesta = verificacionPaqueteService.validarRecepcionPaquete(
                    puntoVenta,
                    cufd,
                    request.getCodigoRecepcion()
            );

            // 4. Actualizar el evento con la respuesta de validación
            evento.setCodigoEstadoValidacion(respuesta.getCodigoEstado());
            evento.setEstadoValidacion("EXITO");
            evento.setFechaProcesamiento(LocalDateTime.now());
            evento.setEtapa("VALIDADO");

            eventoRepository.save(evento);

            return mapToSuccessResponse(respuesta);
        } catch (ProcessException e) {
            return mapToErrorResponse(e.getMessage());
        } catch (RemoteException e) {
            return mapToErrorResponse("Error de comunicación con el servicio de validación: " + e.getMessage());
        }
    }

    private ValidacionPaqueteResponse mapToSuccessResponse(RespuestaRecepcion respuesta) {
        ValidacionPaqueteResponse response = new ValidacionPaqueteResponse();
        response.setCodigoEstado(respuesta.getCodigoEstado());
        response.setCodigoRecepcion(respuesta.getCodigoRecepcion());
        response.setEstado("VALIDACION_EXITOSA");
        response.setMensaje("El paquete de facturas fue validado correctamente");
        return response;
    }

    private ValidacionPaqueteResponse mapToErrorResponse(String mensajeError) {
        ValidacionPaqueteResponse response = new ValidacionPaqueteResponse();
        response.setCodigoEstado(908); // O el código de error que corresponda
        response.setEstado("ERROR_VALIDACION");
        response.setMensaje(mensajeError);
        return response;
    }

    private ValidacionPaqueteResponse mapToResponse(RespuestaRecepcion respuesta) {
        ValidacionPaqueteResponse response = new ValidacionPaqueteResponse();
        response.setCodigoEstado(respuesta.getCodigoEstado());
        response.setCodigoRecepcion(respuesta.getCodigoRecepcion());
        return response;
    }

    public RespuestaRecepcion anularFactura(Long idPuntoVenta, String cuf, String codigoMotivo) throws Exception {
        return anulacionFacturaService.anularFactura(idPuntoVenta, cuf, codigoMotivo);
    }

    public RespuestaRecepcion reversionAnulacionFactura(Long idPuntoVenta, String cuf) throws Exception {
        return reversionFacturaService.reversionAnulacionFactura(idPuntoVenta, cuf);
    }

//    public List<FacturaEntity> getAllFacturas() {
//        return facturaRepository.findAll();
//    }
    public void deleteFacturaById(Long id) {
        facturaRepository.delete(id);
    }
    public List<FacturaDTO> getAllFacturas() {
        return facturaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<FacturaDTO> getFacturaById(Long id) {
        return facturaRepository.findById(id).map(this::convertToDTO);
    }

    private FacturaDTO convertToDTO(FacturaEntity factura) {
        List<FacturaDetalleDTO> detalles = factura.getDetalleList().stream()
                .map(detalle -> new FacturaDetalleDTO(detalle.getDescripcion(), detalle.getSubTotal()))
                .collect(Collectors.toList());

        return new FacturaDTO(
                factura.getId(),
                factura.getCodigoCliente(),
                factura.getNombreRazonSocial(),
                factura.getCuf(),
                factura.getFechaEmision(),
                factura.getEstado(),
                detalles
        );
    }
}