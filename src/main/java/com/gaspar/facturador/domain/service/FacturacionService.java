package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.application.response.PaquetesResponse;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final VentaService ventaService;


    public FacturacionService(
            AppConfig appConfig, GeneraFacturaService generaFacturaService,
            EnvioFacturaService envioFacturaService, EnvioPaquetesService envioPaquetesService, AnulacionFacturaService anulacionFacturaService, ReversionFacturaService reversionFacturaService, FacturaRepository facturaRepository,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository, VentaService ventaService
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
        this.ventaService = ventaService;
    }

    public FacturaResponse emitirFactura(VentaRequest ventaRequest) throws Exception {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) throw new ProcessException("Cufd vigente no encontrado");

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

    public PaquetesResponse recibirPaquetes(VentaRequest ventasRequest) throws Exception {
        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(ventasRequest.getIdPuntoVenta());
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto de venta no encontrado");

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) throw new ProcessException("CUFD vigente no encontrado");

        int cantidadFacturas = ventasRequest.getCantidadFacturas();
        if (cantidadFacturas <= 0) throw new ProcessException("La cantidad de facturas debe ser mayor a 0");

        // Comprimir todos los XMLs en un solo archivo GZIP
//        for (int i = 0; i < cantidadFacturas; i++) {
//            FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventasRequest, cufd.get());
//            this.generaFacturaService.obtenerArchivo(factura);
//        }
        FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventasRequest, cufd.get());
        this.generaFacturaService.obtenerArchivo(factura);

        byte[] xmlsComprimidosZip = FacturaCompressor.comprimirPaqueteFacturas();

        RespuestaRecepcion respuestaRecepcion = envioPaquetesService.enviarPaqueteFacturas(
                puntoVenta.get(),
                cufd.get(),
                xmlsComprimidosZip,
                cantidadFacturas,
                ventasRequest.getCodigoEvento()
        );

        // Retornar la respuesta del SIAT
        PaquetesResponse paquetesResponse = new PaquetesResponse();
        paquetesResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        paquetesResponse.setMensaje(respuestaRecepcion.getMensajesList().get(0).getDescripcion());

        // Limpiar la lista de facturas temporales
        this.generaFacturaService.limpiarFacturasTemporales();

        return paquetesResponse;
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