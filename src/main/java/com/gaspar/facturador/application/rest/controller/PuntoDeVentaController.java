package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.domain.service.PuntoVentaService;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/puntos-venta")
public class PuntoDeVentaController {
    private final IPuntoVentaRepository puntoVentaRepository;

    public PuntoDeVentaController(IPuntoVentaRepository puntoVentaRepository) {
        this.puntoVentaRepository = puntoVentaRepository;
    }

    @GetMapping
    public ResponseEntity<List<PuntoVentaEntity>> getAllPuntosVenta() {
        List<PuntoVentaEntity> puntosVenta = (List<PuntoVentaEntity>) puntoVentaRepository.findAll();
        return new ResponseEntity<>(puntosVenta, HttpStatus.OK);
    }


}
