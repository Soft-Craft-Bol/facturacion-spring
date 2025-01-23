package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.CufdService;
import com.gaspar.facturador.domain.service.CuisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;


@RestController
@RequestMapping("/codigos")
public class CodigosController {

    private final CuisService cuisService;
    private final CufdService cufdService;

    public CodigosController(CuisService cuisService, CufdService cufdService) {
        this.cuisService = cuisService;
        this.cufdService = cufdService;
    }

    @PostMapping("/obtener-cuis/{idPuntoVenta}")
    public ResponseEntity<Object> obtenerCuis(@PathVariable("idPuntoVenta") Integer idPuntoVenta) throws RemoteException {

        this.cuisService.obtenerCuis(idPuntoVenta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/obtener-cufd/{idPuntoVenta}")
    public ResponseEntity<Object> obtenerCufd(@PathVariable("idPuntoVenta") Integer idPuntoVenta) throws RemoteException {

        this.cufdService.obtenerCufd(idPuntoVenta);
        return ResponseEntity.ok().build();
    }
}
