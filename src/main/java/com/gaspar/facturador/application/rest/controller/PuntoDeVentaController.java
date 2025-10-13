package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.PuntoVentaService;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puntos-venta")
public class PuntoDeVentaController {
    private final IPuntoVentaRepository puntoVentaRepository;
    private final PuntoVentaService puntoVentaService;

    public PuntoDeVentaController(IPuntoVentaRepository puntoVentaRepository, PuntoVentaService puntoVentaService) {
        this.puntoVentaRepository = puntoVentaRepository;
        this.puntoVentaService = puntoVentaService;
    }

    @GetMapping
    public ResponseEntity<List<PuntoVentaEntity>> getAllPuntosVenta() {
        List<PuntoVentaEntity> puntosVenta = (List<PuntoVentaEntity>) puntoVentaRepository.findAll();
        return new ResponseEntity<>(puntosVenta, HttpStatus.OK);
    }

    @GetMapping("/{idSucursal}/puntos-venta")
    public ResponseEntity<List<PuntoVentaEntity>> getPuntosVentaBySucursal(
            @PathVariable Integer idSucursal) {
        List<PuntoVentaEntity> puntosVenta = puntoVentaService.getPuntosVentaBySucursalId(idSucursal);
        if (puntosVenta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(puntosVenta);
    }


}
