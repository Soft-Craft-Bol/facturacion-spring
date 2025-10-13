package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.DespachoService;
import com.gaspar.facturador.persistence.DespachoRepository;
import com.gaspar.facturador.persistence.entity.DespachoEntity;
import com.gaspar.facturador.persistence.entity.DespachoItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despachos")
public class DespachoController {

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired DespachoService despachoService;

    @GetMapping
    public ResponseEntity<Page<DespachoEntity>> getDespachos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            @RequestParam(required = false) String transporte,
            @RequestParam(required = false) Long numeroContacto,
            @RequestParam(required = false) Long sucursalOrigen,
            @RequestParam(required = false) Long sucursalDestino,
            @RequestParam(required = false) Long itemId
    ) {
        Page<DespachoEntity> result = despachoService.findAllWithFilters(
                fechaInicio, fechaFin, transporte, numeroContacto, sucursalOrigen, sucursalDestino, itemId, page, size
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoEntity> getById(@PathVariable("id") Long id) {
        Optional<DespachoEntity> despacho = despachoRepository.getById(id);
        return despacho.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


@PostMapping
public ResponseEntity<DespachoEntity> save(@RequestBody DespachoEntity despacho) {
    try {
        DespachoEntity saved = despachoService.registrarDespacho(despacho);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(null);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (despachoRepository.getById(id).isPresent()) {
            despachoRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}