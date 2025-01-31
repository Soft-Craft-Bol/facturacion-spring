package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.RespuestaRecepcion;
import com.gaspar.facturador.application.request.ReversionFacturaRequest;
import com.gaspar.facturador.application.request.VentaRequest;
import com.gaspar.facturador.application.response.FacturaResponse;
import com.gaspar.facturador.domain.service.FacturacionService;
import com.gaspar.facturador.domain.service.RecepcionMasivaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/factura")
public class FacturacionController {

    private final FacturacionService facturacionService;
    private final RecepcionMasivaService recepcionMasivaService;

    public FacturacionController(FacturacionService facturacionService, RecepcionMasivaService recepcionMasivaService) {
        this.facturacionService = facturacionService;
        this.recepcionMasivaService = recepcionMasivaService;
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
    public ResponseEntity<RespuestaRecepcion> recibirFacturasMasivas(
            @RequestParam Long idPuntoVenta,
            @RequestParam byte[] archivo,
            @RequestParam String hashArchivo,
            @RequestParam int cantidadFacturas,
            @RequestParam String codigoEvento
    ) throws Exception {
        RespuestaRecepcion respuesta = recepcionMasivaService.recibirFacturasMasivas(
                idPuntoVenta, archivo, hashArchivo, cantidadFacturas, codigoEvento
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
