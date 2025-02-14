package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.SucursalRepository;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;


    @GetMapping
    public ResponseEntity<List<SucursalEntity>> findAll(){
        Iterable<SucursalEntity> sucusales = sucursalRepository.findAll();
        return ResponseEntity.ok((List<SucursalEntity>) sucusales);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SucursalEntity> getSucursalById(@PathVariable Integer id){
        Optional<SucursalEntity> sucursal = sucursalRepository.findById(id);
        return sucursal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<SucursalEntity> createSucursal(@RequestBody SucursalEntity sucursal) {
        SucursalEntity savedSucursal = sucursalRepository.save(sucursal);
        return ResponseEntity.ok(savedSucursal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SucursalEntity> updateSucursal(@PathVariable Integer id, @RequestBody SucursalEntity sucursal) {
        if (!sucursalRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sucursal.setId(id);
        SucursalEntity updatedSucursal = sucursalRepository.save(sucursal);
        return ResponseEntity.ok(updatedSucursal);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Integer id) {
        if (!sucursalRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sucursalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
