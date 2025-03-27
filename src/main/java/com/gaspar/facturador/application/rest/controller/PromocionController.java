package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.crud.PromocionCrudRepository;
import com.gaspar.facturador.persistence.entity.PromocionEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promocion")
public class PromocionController {
    @Autowired
    PromocionCrudRepository promocionCrudRepository;
    @GetMapping
    public ResponseEntity<List<PromocionEntity>> getAllPromocion(){
        List<PromocionEntity> promociones = (List<PromocionEntity>) promocionCrudRepository.findAll();
        return ResponseEntity.ok(promociones);
    }
    @PostMapping
    public ResponseEntity<PromocionEntity> savePromocion(@RequestBody PromocionEntity promocion) {
        if (promocion.getItem() == null || promocion.getItem().getId() == null ||
                promocion.getSucursal() == null || promocion.getSucursal().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        PromocionEntity promocionEntity = promocionCrudRepository.save(promocion);
        return ResponseEntity.ok(promocionEntity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocion(@PathVariable Long id) {
        if (!promocionCrudRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        promocionCrudRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
