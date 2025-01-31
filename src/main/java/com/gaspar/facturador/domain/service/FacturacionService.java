package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.domain.FacturaElectronicaCompraVenta;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.emision.EnvioFacturaService;
import com.gaspar.facturador.domain.service.emision.GeneraFacturaService;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FacturacionService {

    private final GeneraFacturaService generaFacturaService;
    private final EnvioFacturaService envioFacturaService;
    private final AnulacionFacturaService anulacionFacturaService;
    private final ReversionFacturaService reversionFacturaService;
    private final RecepcionMasivaService recepcionMasivaService;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;

    public FacturacionService(
            GeneraFacturaService generaFacturaService,
            EnvioFacturaService envioFacturaService, AnulacionFacturaService anulacionFacturaService, ReversionFacturaService reversionFacturaService, RecepcionMasivaService recepcionMasivaService,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository
    ) {
        this.generaFacturaService = generaFacturaService;
        this.envioFacturaService = envioFacturaService;
        this.anulacionFacturaService = anulacionFacturaService;
        this.reversionFacturaService = reversionFacturaService;
        this.recepcionMasivaService = recepcionMasivaService;
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

        // *** Aquí imprimimos el XML sin firmar para depuración ***
        //this.generaFacturaService.imprimirXmlSinFirmar(factura); // Asegúrate de tener este método en GeneraFacturaService

// *** Aquí imprimimos el XML firmado para depuración ***
       // this.generaFacturaService.imprimirXmlFirmado(factura);

        // Continuar con el flujo normal
        byte[] xmlComprimidoZip = this.generaFacturaService.obtenerArchivo(factura);
        RespuestaRecepcion respuestaRecepcion = this.envioFacturaService.enviar(puntoVenta.get(), cufd.get(), xmlComprimidoZip);

        // Construir la respuesta
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        facturaResponse.setCuf(factura.getCabecera().getCuf());
        facturaResponse.setNumeroFactura(factura.getCabecera().getNumeroFactura());

        return facturaResponse;
    }

    public RespuestaRecepcion anularFactura(Long idPuntoVenta, String cuf, String codigoMotivo) throws Exception {
        return anulacionFacturaService.anularFactura(idPuntoVenta, cuf, codigoMotivo);
    }

    public RespuestaRecepcion revertirFactura(Long idPuntoVenta, String cuf, String codigoMotivo) throws Exception {
        return reversionFacturaService.revertirFactura(idPuntoVenta, cuf, codigoMotivo);
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
}
