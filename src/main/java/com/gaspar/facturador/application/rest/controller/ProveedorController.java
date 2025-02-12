package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.ProveedorRepository;
import com.gaspar.facturador.persistence.entity.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorRepository proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorEntity>> getAllProveedores() {
        List<ProveedorEntity> proveedores = proveedorService.findAll();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorEntity> getProveedorById(@PathVariable long id) {
        Optional<ProveedorEntity> proveedor = proveedorService.findById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProveedorEntity> createProveedor(@RequestBody ProveedorEntity proveedor) {
        ProveedorEntity savedProveedor = proveedorService.save(proveedor);
        return ResponseEntity.ok(savedProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorEntity> updateProveedor(@PathVariable long id, @RequestBody ProveedorEntity proveedor) {
        if (!proveedorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        proveedor.setId(id);
        ProveedorEntity updatedProveedor = proveedorService.save(proveedor);
        return ResponseEntity.ok(updatedProveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable long id) {
        if (!proveedorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}