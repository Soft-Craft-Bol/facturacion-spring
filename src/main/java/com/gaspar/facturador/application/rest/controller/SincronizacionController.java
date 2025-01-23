package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.SincronizacionCatalogosParametrosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;


@RestController
@RequestMapping("/sincronizar")
public class SincronizacionController {

    private final SincronizacionCatalogosParametrosService sincronizacionService;

    public SincronizacionController(SincronizacionCatalogosParametrosService sincronizacionService) {
        this.sincronizacionService = sincronizacionService;
    }

    @PostMapping("/catalogos")
    public ResponseEntity<Object> sincronizarCatalogos() throws RemoteException {
        this.sincronizacionService.sincronizarCatalogos();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/parametros")
    public ResponseEntity<Object> sincronizarParametros() throws Exception {

        this.sincronizacionService.sincronizarParametros();
        return ResponseEntity.ok().build();
    }
}
