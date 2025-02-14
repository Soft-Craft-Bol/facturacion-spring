package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.DespachoRepository;
import com.gaspar.facturador.persistence.entity.DespachoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despachos")
public class DespachoController {
    @Autowired
    private DespachoRepository despachoService;
    @GetMapping
    public List<DespachoEntity> getAll() {
        return despachoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoEntity> getById(@PathVariable("id") Long id) {
        Optional<DespachoEntity> despacho = despachoService.getById(id);
        return despacho.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DespachoEntity> save(@RequestBody DespachoEntity despacho) {
        DespachoEntity savedDespacho = despachoService.save(despacho);
        return new ResponseEntity<>(savedDespacho, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (despachoService.getById(id).isPresent()) {
            despachoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
