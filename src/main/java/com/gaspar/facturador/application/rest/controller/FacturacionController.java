package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.domain.service.FacturacionService;
import com.gaspar.facturador.persistence.dto.FacturaDTO;
import jakarta.validation.Valid;
import com.gaspar.facturador.persistence.FacturaRepository;
import com.gaspar.facturador.persistence.entity.FacturaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/factura")
//@PreAuthorize("denyAll()")
public class FacturacionController {

    private final FacturacionService facturacionService;
    private FacturaRepository facturaRepository;

    public FacturacionController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    @PostMapping("/emitir")
    public ResponseEntity<FacturaResponse> emitirFactura(@Valid @RequestBody VentaRequest ventaRequest) throws Exception {
        FacturaResponse facturaResponse = this.facturacionService.emitirFactura(ventaRequest);
        return new ResponseEntity<>(facturaResponse, HttpStatus.CREATED);
    }

    @PostMapping("/anular")
    public ResponseEntity<RespuestaRecepcion> anularFactura(
            @RequestParam Long idPuntoVenta,
            @RequestParam String cuf,
            @RequestParam String codigoMotivo
    ) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.anularFactura(idPuntoVenta, cuf, codigoMotivo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/reversion-anulacion")
    public ResponseEntity<RespuestaRecepcion> reversionAnulacionFactura(
            @RequestParam Long idPuntoVenta,
            @RequestParam String cuf
    ) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.reversionAnulacionFactura(idPuntoVenta, cuf);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PostMapping("/recepcion-masiva")
    public ResponseEntity<RespuestaRecepcion> enviarPaqueteFacturas(
            @RequestParam Long idPuntoVenta,
            @RequestParam int cantidadFacturas,
            @RequestBody byte[] archivoComprimido
    ) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.enviarPaqueteFacturas(idPuntoVenta, archivoComprimido, cantidadFacturas);
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
