package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.EgresosRepository;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/egresos")
public class EgresosController {
    @Autowired
    private EgresosRepository egresosService;

    @GetMapping
    public ResponseEntity<List<EgresosEntity>> getAllEgresos() {
        List<EgresosEntity> egresos = egresosService.findAll();
        return ResponseEntity.ok(egresos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EgresosEntity> getEgresoById(@PathVariable long id) {
        Optional<EgresosEntity> egreso = egresosService.findById(id);
        return egreso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EgresosEntity> createEgreso(@RequestBody EgresosEntity egreso) {
        EgresosEntity savedEgreso = egresosService.save(egreso);
        return ResponseEntity.ok(savedEgreso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EgresosEntity> updateEgreso(@PathVariable long id, @RequestBody EgresosEntity egreso) {
        if (!egresosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        egreso.setId(id);
        EgresosEntity updatedEgreso = egresosService.save(egreso);
        return ResponseEntity.ok(updatedEgreso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEgreso(@PathVariable long id) {
        if (!egresosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        egresosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}