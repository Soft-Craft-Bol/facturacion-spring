package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.EnvioPaquetesService;
import com.gaspar.facturador.application.rest.dto.EnvioPaqueteRequest;
import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.RespuestaRecepcion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.rmi.RemoteException;

@RestController
@RequestMapping("/masiva")
public class EnvioPaqueteController {

    private final EnvioPaquetesService envioPaquetesService;

    public EnvioPaqueteController(EnvioPaquetesService envioPaquetesService) {
        this.envioPaquetesService = envioPaquetesService;
    }

    @PostMapping("/enviar-paquete")
    public ResponseEntity<RespuestaRecepcion> enviarPaqueteFacturas(
            @RequestPart("archivo") MultipartFile archivo,
            @RequestPart("datos") EnvioPaqueteRequest request
    ) throws RemoteException {
        byte[] archivoBytes;
        try {
            archivoBytes = archivo.getBytes();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        RespuestaRecepcion respuesta = envioPaquetesService.enviarPaqueteFacturas(
                request.getIdPuntoVenta(), archivoBytes, request.getCantidadFacturas()
        );
        return ResponseEntity.ok(respuesta);
    }
}
