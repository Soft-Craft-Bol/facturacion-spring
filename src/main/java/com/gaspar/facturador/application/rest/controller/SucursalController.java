package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    private static final Logger logger = LoggerFactory.getLogger(SucursalController.class);

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public ResponseEntity<List<SucursalEntity>> findAll() {
        try {
            Iterable<SucursalEntity> sucursales = sucursalRepository.findAll();
            return ResponseEntity.ok((List<SucursalEntity>) sucursales);
        } catch (Exception e) {
            logger.error("Error fetching sucursales", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalEntity> getSucursalById(@PathVariable Integer id) {
        try {
            Optional<SucursalEntity> sucursal = sucursalRepository.findById(id);
            return sucursal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching sucursal by id", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SucursalEntity> createSucursal(@RequestBody SucursalEntity sucursal) {
        try {
            SucursalEntity savedSucursal = sucursalRepository.save(sucursal);
            return ResponseEntity.ok(savedSucursal);
        } catch (Exception e) {
            logger.error("Error creating sucursal", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalEntity> updateSucursal(@PathVariable Integer id, @RequestBody SucursalEntity sucursal) {
        try {
            if (!sucursalRepository.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            sucursal.setId(id);
            SucursalEntity updatedSucursal = sucursalRepository.save(sucursal);
            return ResponseEntity.ok(updatedSucursal);
        } catch (Exception e) {
            logger.error("Error updating sucursal", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Integer id) {
        try {
            if (!sucursalRepository.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            sucursalRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting sucursal", e);
            return ResponseEntity.status(500).build();
        }
    }
}
