package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.repository.SucursalItemCrudRepository;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sucursal-items")
public class SucursalItemController {
    @Autowired
    private SucursalItemCrudRepository sucursalItemCrudRepository;

    @GetMapping
    public ResponseEntity<Iterable<SucursalItemEntity>> findAll() {
        return ResponseEntity.ok(sucursalItemCrudRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalItemEntity> findById(@PathVariable Integer id) {
        Optional<SucursalItemEntity> sucursalItem = sucursalItemCrudRepository.findById(id);
        return sucursalItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SucursalItemEntity> create(@RequestBody SucursalItemEntity sucursalItem) {
        SucursalItemEntity savedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(savedSucursalItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalItemEntity> update(@PathVariable Integer id, @RequestBody SucursalItemEntity sucursalItem) {
        if (!sucursalItemCrudRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sucursalItem.setId(id);
        SucursalItemEntity updatedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(updatedSucursalItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!sucursalItemCrudRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sucursalItemCrudRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-cantidad")
    public ResponseEntity<SucursalItemEntity> addCantidad(@PathVariable Integer id, @RequestParam Integer cantidad) {
        Optional<SucursalItemEntity> sucursalItemOptional = sucursalItemCrudRepository.findById(id);
        if (!sucursalItemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        SucursalItemEntity sucursalItem = sucursalItemOptional.get();
        sucursalItem.setCantidad(sucursalItem.getCantidad() + cantidad);
        SucursalItemEntity updatedSucursalItem = sucursalItemCrudRepository.save(sucursalItem);
        return ResponseEntity.ok(updatedSucursalItem);
    }
}