package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.RespuestaFechaHora;
import com.gaspar.facturador.domain.service.SincronizacionCatalogosParametrosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

@RestController
@RequestMapping("/sincronizar")
public class SincronizacionController {

    private final SincronizacionCatalogosParametrosService sincronizacionService;

    public SincronizacionController(SincronizacionCatalogosParametrosService sincronizacionService) {
        this.sincronizacionService = sincronizacionService;
    }

    @PostMapping("/catalogos/{idPuntoVenta}")
    public ResponseEntity<Object> sincronizarCatalogos(@PathVariable Long idPuntoVenta) throws RemoteException {
        this.sincronizacionService.sincronizarCatalogos(idPuntoVenta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/parametros/{idPuntoVenta}")
    public ResponseEntity<Object> sincronizarParametros(@PathVariable Long idPuntoVenta) throws Exception {
        this.sincronizacionService.sincronizarParametros(idPuntoVenta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fecha-hora/{idPuntoVenta}")
    public RespuestaFechaHora obtenerFechaHora(@PathVariable Long idPuntoVenta) throws Exception {
        return sincronizacionService.sincronizarFechaHora(idPuntoVenta);
    }

    @PostMapping("/mensajes-servicios/{idPuntoVenta}")
    public ResponseEntity<Object> obtenerMensajesServicios(@PathVariable Long idPuntoVenta) throws Exception {
        this.sincronizacionService.sincronizarMensajesServicios(idPuntoVenta);
        return ResponseEntity.ok().build();
    }


}
