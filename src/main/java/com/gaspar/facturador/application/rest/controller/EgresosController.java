package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.EgresosRepository;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/egresos")
public class EgresosController {
    @Autowired
    private EgresosRepository egresosRepository;

    @GetMapping
    public List<EgresosEntity> getAllEgresos() {
        return egresosRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EgresosEntity> getEgresoById(@PathVariable Long id) {
        Optional<EgresosEntity> egreso = egresosRepository.getById(id);
        return egreso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EgresosEntity> createEgreso(@RequestBody EgresosEntity egreso) {
        EgresosEntity savedEgreso = egresosRepository.save(egreso);
        return new ResponseEntity<>(savedEgreso, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EgresosEntity> updateEgreso(@PathVariable Long id, @RequestBody EgresosEntity egresoDetails) {
        EgresosEntity updatedEgreso = egresosRepository.update(id, egresoDetails);
        if (updatedEgreso != null) {
            return ResponseEntity.ok(updatedEgreso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEgreso(@PathVariable Long id) {
        egresosRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}