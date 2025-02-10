package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.domain.DetalleCompraVenta;
import com.gaspar.facturador.domain.FacturaElectronicaCompraVenta;
import com.gaspar.facturador.domain.helpers.Utils;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.emision.EnvioFacturaService;
import com.gaspar.facturador.domain.service.emision.GeneraFacturaService;
import com.gaspar.facturador.persistence.FacturaRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.FacturaDetalleEntity;
import com.gaspar.facturador.persistence.entity.FacturaEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FacturacionService {

    private final GeneraFacturaService generaFacturaService;
    private final EnvioFacturaService envioFacturaService;
    private final AnulacionFacturaService anulacionFacturaService;
    //private final ReversionFacturaService reversionFacturaService;
    private final RecepcionMasivaService recepcionMasivaService;
    private final FacturaRepository facturaRepository;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;

    public FacturacionService(
            GeneraFacturaService generaFacturaService,
            EnvioFacturaService envioFacturaService, AnulacionFacturaService anulacionFacturaService, RecepcionMasivaService recepcionMasivaService, FacturaRepository facturaRepository,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository
    ) {
        this.generaFacturaService = generaFacturaService;
        this.envioFacturaService = envioFacturaService;
        this.anulacionFacturaService = anulacionFacturaService;
        this.recepcionMasivaService = recepcionMasivaService;
        this.facturaRepository = facturaRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
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
        //facturaEntity.setEmailCliente(factura.getCabecera().getEmailCliente());

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
        facturaRepository.save(facturaEntity);
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

// In FacturacionService
       //&& factura = facturaRepository.save(facturaResponse);

        return facturaResponse;
    }

    public RespuestaRecepcion anularFactura(Long idPuntoVenta, String cuf, String codigoMotivo) throws Exception {
        return anulacionFacturaService.anularFactura(idPuntoVenta, cuf, codigoMotivo);
    }

    public RespuestaRecepcion enviarPaqueteFacturas(Long idPuntoVenta, byte[] archivoComprimido, int cantidadFacturas) throws Exception {
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) {
            throw new ProcessException("Punto de venta no encontrado.");
        }

        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) {
            throw new ProcessException("CUFD vigente no encontrado.");
        }

        return recepcionMasivaService.enviarPaqueteFacturas(puntoVenta.get(), cufd.get(), archivoComprimido, cantidadFacturas);
    }
    public List<FacturaEntity> getAllFacturas() {
        return facturaRepository.findAll();
    }
    public void deleteFacturaById(Long id) {
        facturaRepository.delete(id);
    }
}
