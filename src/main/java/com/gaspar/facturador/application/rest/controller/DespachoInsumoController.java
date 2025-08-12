package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.DespachoInsumoService;
import com.gaspar.facturador.persistence.entity.DespachoInsumoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despachos-insumos")
@RequiredArgsConstructor
public class DespachoInsumoController {
    private final DespachoInsumoService despachoInsumoService;

    @GetMapping
    public ResponseEntity<List<DespachoInsumoEntity>> getAll() {
        return ResponseEntity.ok(despachoInsumoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoInsumoEntity> getById(@PathVariable Long id) {
        return despachoInsumoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DespachoInsumoEntity> create(@RequestBody DespachoInsumoEntity despacho) {
        return new ResponseEntity<>(
                despachoInsumoService.save(despacho),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}/recibir")
    public ResponseEntity<DespachoInsumoEntity> recibirDespacho(@PathVariable Long id) {
        return ResponseEntity.ok(despachoInsumoService.recibirDespacho(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        despachoInsumoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}