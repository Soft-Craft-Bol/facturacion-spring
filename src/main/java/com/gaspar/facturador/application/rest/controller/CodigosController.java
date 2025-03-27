package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.CufdService;
import com.gaspar.facturador.domain.service.CuisService;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.util.List;


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

    @GetMapping("/obtener-cufds-anteriores/{idPuntoVenta}")
    public ResponseEntity<List<CufdEntity>> obtenerCufdsAnteriores(@PathVariable("idPuntoVenta") Integer idPuntoVenta) {
        List<CufdEntity> cufdsAnteriores = this.cufdService.obtenerCufdsAnteriores(idPuntoVenta);
        return ResponseEntity.ok(cufdsAnteriores);
    }
}
