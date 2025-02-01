package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.RespuestaRecepcion;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.domain.service.FacturacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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

    @PostMapping("/anular")
    public ResponseEntity<RespuestaRecepcion> anularFactura(
            @RequestParam Long idPuntoVenta,
            @RequestParam String cuf,
            @RequestParam String codigoMotivo
    ) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.anularFactura(idPuntoVenta, cuf, codigoMotivo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/revertir")
    public ResponseEntity<RespuestaRecepcion> revertirFactura(
            @RequestParam Long idPuntoVenta,
            @RequestParam String cuf,
            @RequestParam String codigoMotivo
    ) throws Exception {
        RespuestaRecepcion respuesta = this.facturacionService.revertirFactura(
                idPuntoVenta,
                cuf,
                codigoMotivo
        );
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

}
