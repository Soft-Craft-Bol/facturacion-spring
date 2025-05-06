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
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.emision.EnvioFacturaService;
import com.gaspar.facturador.domain.service.emision.GeneraFacturaService;
import com.gaspar.facturador.persistence.FacturaRepository;
import com.gaspar.facturador.persistence.dto.FacturaDetalleDTO;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.FacturaDetalleEntity;
import com.gaspar.facturador.persistence.entity.FacturaEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.utils.FacturaCompressor;
import org.springframework.stereotype.Service;
import com.gaspar.facturador.persistence.dto.FacturaDTO;

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
    private final VerificacionPaqueteService verificacionPaqueteService;
    private final VentaService ventaService;
    private final CufdService cufdService;


    public FacturacionService(
            AppConfig appConfig, GeneraFacturaService generaFacturaService,
            EnvioFacturaService envioFacturaService, EnvioPaquetesService envioPaquetesService, AnulacionFacturaService anulacionFacturaService, ReversionFacturaService reversionFacturaService, FacturaRepository facturaRepository,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository, VerificacionPaqueteService verificacionPaqueteService, VentaService ventaService, CufdService cufdService
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
        this.verificacionPaqueteService = verificacionPaqueteService;
        this.ventaService = ventaService;
        this.cufdService = cufdService;
    }

    public FacturaResponse emitirFactura(VentaRequest ventaRequest) throws Exception {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());

        if (cufd.isEmpty() || cufd.get().getFechaVigencia().isBefore(LocalDateTime.now())) {
            this.cufdService.obtenerCufd(ventaRequest.getIdPuntoVenta());
            cufd = cufdRepository.findActual(puntoVenta.get());

            // Validar nuevamente después de obtener uno nuevo
            if (cufd.isEmpty() || cufd.get().getFechaVigencia().isBefore(LocalDateTime.now())) {
                throw new ProcessException("No se pudo obtener un CUFD vigente");
            }
        }

        // Generar la factura
        FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventaRequest, cufd.get());


        // Convertir FacturaElectronicaCompraVenta a FacturaEntity
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setNitEmisor(factura.getCabecera().getNitEmisor());
        facturaEntity.setRazonSocialEmisor(factura.getCabecera().getRazonSocialEmisor());
        facturaEntity.setMunicipio(factura.getCabecera().getMunicipio());
        facturaEntity.setTelefono(factura.getCabecera().getTelefono());
        facturaEntity.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        facturaEntity.setCuf(factura.getCabecera().getCuf());
        facturaEntity.setCufd(factura.getCabecera().getCufd());
        facturaEntity.setCodigoSucursal(factura.getCabecera().getCodigoSucursal());
        facturaEntity.setDireccion(factura.getCabecera().getDireccion());
        facturaEntity.setCodigoPuntoVenta(factura.getCabecera().getCodigoPuntoVenta());
        facturaEntity.setFechaEmision(Utils.xMLGregorianCalendarToLocalDateTime(factura.getCabecera().getFechaEmision()));
        facturaEntity.setNombreRazonSocial(factura.getCabecera().getNombreRazonSocial());
        facturaEntity.setCodigoTipoDocumentoIdentidad(factura.getCabecera().getCodigoTipoDocumentoIdentidad());
        facturaEntity.setNumeroDocumento(factura.getCabecera().getNumeroDocumento());
        facturaEntity.setComplemento(factura.getCabecera().getComplemento());
        facturaEntity.setCodigoCliente(factura.getCabecera().getCodigoCliente());
        facturaEntity.setCodigoMetodoPago(factura.getCabecera().getCodigoMetodoPago());
        facturaEntity.setNumeroTarjeta(factura.getCabecera().getNumeroTarjeta());
        facturaEntity.setMontoTotal(factura.getCabecera().getMontoTotal());
        facturaEntity.setMontoTotalSujetoIva(factura.getCabecera().getMontoTotalSujetoIva());
        facturaEntity.setMontoGiftCard(factura.getCabecera().getMontoGiftCard());
        facturaEntity.setDescuentoAdicional(factura.getCabecera().getDescuentoAdicional());
        facturaEntity.setCodigoExcepcion(factura.getCabecera().getCodigoExcepcion());
        facturaEntity.setCafc(factura.getCabecera().getCafc());
        facturaEntity.setCodigoMoneda(factura.getCabecera().getCodigoMoneda());
        facturaEntity.setTipoCambio(factura.getCabecera().getTipoCambio());
        facturaEntity.setMontoTotalMoneda(factura.getCabecera().getMontoTotalMoneda());
        facturaEntity.setLeyenda(factura.getCabecera().getLeyenda());
        facturaEntity.setUsuario(factura.getCabecera().getUsuario());
        facturaEntity.setCodigoDocumentoSector(factura.getCabecera().getCodigoDocumentoSector());
        facturaEntity.setEstado("EMITIDA"); // Estado inicial

        // Convertir detalles
        List<FacturaDetalleEntity> detalles = new ArrayList<>();
        for (DetalleCompraVenta detalle : factura.getDetalle()) {
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
            detalleEntity.setFactura(facturaEntity);
            detalles.add(detalleEntity);
        }
        facturaEntity.setDetalleList(detalles);

        // Guardar la factura y sus detalles
        FacturaEntity facturaGuardada = facturaRepository.save(facturaEntity);

        // Obtener el ID de la factura recién creada
        Long idFactura = facturaGuardada.getId();


        // Convertir VentaRequest a VentaSinFacturaRequest
        VentaSinFacturaRequest ventaSinFacturaRequest = new VentaSinFacturaRequest();
        ventaSinFacturaRequest.setIdPuntoVenta(Long.valueOf(ventaRequest.getIdPuntoVenta()));
        ventaSinFacturaRequest.setCliente(String.valueOf(ventaRequest.getIdCliente()));
        ventaSinFacturaRequest.setTipoComprobante(ventaRequest.getTipoComprobante());
        ventaSinFacturaRequest.setMetodoPago(ventaRequest.getMetodoPago());
        ventaSinFacturaRequest.setUsername(ventaRequest.getUsername());
        ventaSinFacturaRequest.setDetalle(ventaRequest.getDetalle());
        ventaSinFacturaRequest.setIdfactura(idFactura);

        // Guardar la venta en la tabla de ventas
        ventaService.saveVenta(ventaSinFacturaRequest);

        // Obtener el XML sin firmar
        byte[] xmlBytes = this.generaFacturaService.getXmlBytes(factura);
        String xmlContent = new String(xmlBytes);

        // Continuar con el flujo normal
        byte[] xmlComprimidoZip = this.generaFacturaService.obtenerArchivo(factura);
        RespuestaRecepcion respuestaRecepcion = this.envioFacturaService.enviar(puntoVenta.get(), cufd.get(), xmlComprimidoZip);

        // Construir la respuesta
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        facturaResponse.setCuf(factura.getCabecera().getCuf());
        facturaResponse.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        facturaResponse.setXmlContent(xmlContent);

        return facturaResponse;
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
        byte[] xmlComprimidoZip = this.generaFacturaService.obtenerArchivo(factura);

        // Construir la respuesta
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setCuf(factura.getCabecera().getCuf());
        facturaResponse.setNumeroFactura(factura.getCabecera().getNumeroFactura());
        facturaResponse.setXmlContent(xmlContent);

        return facturaResponse;
    }

    public PaquetesResponse enviarPaquetes(EnvioPaqueteRequest envioPaqueteRequest) throws IOException {
        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(envioPaqueteRequest.idPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto de venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) throw new ProcessException("CUFD vigente no encontrado");

        int cantidadFacturas = Math.toIntExact(FacturaCompressor.getCantidadArchivosXML());


        byte[] xmlsComprimidosZip = FacturaCompressor.comprimirPaqueteFacturas();

        RespuestaRecepcion respuestaRecepcion = envioPaquetesService.enviarPaqueteFacturas(
                puntoVenta.get(),
                cufd.get(),
                xmlsComprimidosZip,
                cantidadFacturas,
                envioPaqueteRequest.codigoEvento(),
                envioPaqueteRequest.cafc()
        );

        // Crear y personalizar la respuesta
        PaquetesResponse paquetesResponse = new PaquetesResponse();
        paquetesResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        paquetesResponse.setCodigoRecepcion(respuestaRecepcion.getCodigoRecepcion());
        paquetesResponse.setCodigoEvento(envioPaqueteRequest.codigoEvento());
        paquetesResponse.setCantidadFacturasAlmacenadas(cantidadFacturas);

        // Personalizar mensajes según el código de estado
        if (respuestaRecepcion.getCodigoEstado() == 901) {
            paquetesResponse.setMensaje("Paquete recibido correctamente por el SIAT. Estado: PENDIENTE DE PROCESAMIENTO");
        } else if (respuestaRecepcion.getCodigoEstado() == 902) {
            paquetesResponse.setMensaje("Paquete recibido con observaciones. Estado: PENDIENTE DE VALIDACIÓN");
        } else if (respuestaRecepcion.getCodigoEstado() == 903) {
            paquetesResponse.setMensaje("Paquete rechazado por el SIAT");
        } else {
            paquetesResponse.setMensaje("Estado del paquete desconocido");
        }

        // Si hay mensajes adicionales del SIAT, concatenarlos
        if (respuestaRecepcion.getMensajesList() != null && !respuestaRecepcion.getMensajesList().isEmpty()) {
            String mensajeOriginal = paquetesResponse.getMensaje();
            StringBuilder mensajeDetallado = new StringBuilder(mensajeOriginal);
            mensajeDetallado.append(" Detalles: ");
            for (MensajeRecepcion mensaje : respuestaRecepcion.getMensajesList()) {
                mensajeDetallado.append(mensaje.getDescripcion()).append(". ");
            }
            paquetesResponse.setMensaje(mensajeDetallado.toString());
        }

        // Limpiar la lista de facturas temporales
        this.generaFacturaService.limpiarFacturasTemporales();

        return paquetesResponse;
    }

    public PaquetesResponse recibirPaquetes(VentaRequest ventasRequest) throws Exception {
        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(ventasRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto de venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) throw new ProcessException("CUFD vigente no encontrado");

        int cantidadFacturas = Math.toIntExact(ventasRequest.getCantidadFacturas());
        if (cantidadFacturas <= 0) throw new ProcessException("La cantidad de facturas debe ser mayor a 0");

        FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventasRequest, cufd.get());
        this.generaFacturaService.obtenerArchivo(factura);

        byte[] xmlsComprimidosZip = FacturaCompressor.comprimirPaqueteFacturas();

        RespuestaRecepcion respuestaRecepcion = envioPaquetesService.enviarPaqueteFacturas(
                puntoVenta.get(),
                cufd.get(),
                xmlsComprimidosZip,
                cantidadFacturas,
                ventasRequest.getCodigoEvento(),
                ventasRequest.getCafc()
        );

        // Crear y personalizar la respuesta
        PaquetesResponse paquetesResponse = new PaquetesResponse();
        paquetesResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        paquetesResponse.setCodigoRecepcion(respuestaRecepcion.getCodigoRecepcion());
        paquetesResponse.setCodigoEvento(ventasRequest.getCodigoEvento());
        paquetesResponse.setCantidadFacturasAlmacenadas(cantidadFacturas);

        // Personalizar mensajes según el código de estado
        if (respuestaRecepcion.getCodigoEstado() == 901) {
            paquetesResponse.setMensaje("Paquete recibido correctamente por el SIAT. Estado: PENDIENTE DE PROCESAMIENTO");
        } else if (respuestaRecepcion.getCodigoEstado() == 902) {
            paquetesResponse.setMensaje("Paquete recibido con observaciones. Estado: PENDIENTE DE VALIDACIÓN");
        } else if (respuestaRecepcion.getCodigoEstado() == 903) {
            paquetesResponse.setMensaje("Paquete rechazado por el SIAT");
        } else {
            paquetesResponse.setMensaje("Estado del paquete desconocido");
        }

        // Si hay mensajes adicionales del SIAT, concatenarlos
        if (respuestaRecepcion.getMensajesList() != null && !respuestaRecepcion.getMensajesList().isEmpty()) {
            String mensajeOriginal = paquetesResponse.getMensaje();
            StringBuilder mensajeDetallado = new StringBuilder(mensajeOriginal);
            mensajeDetallado.append(" Detalles: ");
            for (MensajeRecepcion mensaje : respuestaRecepcion.getMensajesList()) {
                mensajeDetallado.append(mensaje.getDescripcion()).append(". ");
            }
            paquetesResponse.setMensaje(mensajeDetallado.toString());
        }

        // Limpiar la lista de facturas temporales
        this.generaFacturaService.limpiarFacturasTemporales();

        return paquetesResponse;
    }

    public ValidacionPaqueteResponse validarRecepcionPaquete(ValidacionPaqueteRequest request) throws RemoteException {
        try {
            PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(request.getIdPuntoVenta())
                    .orElseThrow(() -> new ProcessException("Punto de venta no encontrado con ID: " + request.getIdPuntoVenta()));

            CufdEntity cufd = cufdRepository.findActual(puntoVenta)
                    .orElseThrow(() -> new ProcessException("No se encontró un CUFD vigente para el punto de venta"));

            RespuestaRecepcion respuesta = verificacionPaqueteService.validarRecepcionPaquete(
                    puntoVenta,
                    cufd,
                    request.getCodigoRecepcion()
            );
            // Mapear respuesta exitosa
            return mapToSuccessResponse(respuesta);
        } catch (ProcessException e) {
            // Mapear respuesta fallida
            return mapToErrorResponse(e.getMessage());
        } catch (RemoteException e) {
            // Manejar errores de conexión
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