package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.application.response.PaquetesResponse;
import com.gaspar.facturador.application.rest.dto.AnulacionRequest;
import com.gaspar.facturador.application.rest.dto.ReversionAnulacionRequest;
import com.gaspar.facturador.domain.service.FacturacionService;
import com.gaspar.facturador.persistence.dto.FacturaDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/factura")
//@PreAuthorize("denyAll()")
public class FacturacionController {

    private final FacturacionService facturacionService;

    public FacturacionController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    @PostMapping("/emitir")
    public ResponseEntity<FacturaResponse> emitirFactura(@Valid @RequestBody VentaRequest ventaRequest) throws Exception {
        FacturaResponse facturaResponse = this.facturacionService.emitirFactura(ventaRequest);
        return new ResponseEntity<>(facturaResponse, HttpStatus.CREATED);
    }

    @PostMapping("/enviar-paquete")
    public ResponseEntity<PaquetesResponse> enviarPaqueteFacturas(@Valid @RequestBody VentaRequest ventasRequest) throws Exception {
        PaquetesResponse paquetesResponse = this.facturacionService.recibirPaquetes(ventasRequest);
        return ResponseEntity.ok(paquetesResponse);
    }



    @PostMapping("/anular")
    public ResponseEntity<RespuestaRecepcion> anularFactura(@Valid @RequestBody AnulacionRequest anulacionRequest) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.anularFactura(
                anulacionRequest.getIdPuntoVenta(),
                anulacionRequest.getCuf(),
                anulacionRequest.getCodigoMotivo()
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/reversion-anulacion")
    public ResponseEntity<RespuestaRecepcion> reversionAnulacionFactura(@Valid @RequestBody ReversionAnulacionRequest reversionAnulacionRequest) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.reversionAnulacionFactura(
                reversionAnulacionRequest.getIdPuntoVenta(),
                reversionAnulacionRequest.getCuf()
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturacionService.getAllFacturas();
        return ResponseEntity.ok(facturas);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacturaById(@PathVariable Long id) {
        facturacionService.deleteFacturaById(id);
        return ResponseEntity.noContent().build();
    }
}
