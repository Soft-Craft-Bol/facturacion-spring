package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.crud.PromocionCrudRepository;
import com.gaspar.facturador.persistence.entity.PromocionEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> savePromocion(@RequestBody PromocionEntity promocionEntity,
                                           @RequestParam(defaultValue = "false") boolean confirmar) {
        if (promocionEntity.getItem() == null || promocionEntity.getItem().getId() == null ||
                promocionEntity.getSucursal() == null || promocionEntity.getSucursal().getId() == null) {
            return ResponseEntity.badRequest().body("Item o Sucursal no pueden ser nulos");
        }
        List<PromocionEntity> existentes = promocionCrudRepository.findByItemIdAndSucursalId(
                promocionEntity.getItem().getId(), promocionEntity.getSucursal().getId());

        if (!existentes.isEmpty() && !confirmar) {
            return ResponseEntity.status(409).body("Ya existe una promoción para este producto en esta sucursal. ¿Desea actualizarla?");
        }

        if (!existentes.isEmpty() && confirmar) {
            // Tomamos la primera para actualizar
            PromocionEntity existentePromocion = existentes.get(0);
            existentePromocion.setDescuento(promocionEntity.getDescuento());
            promocionEntity = existentePromocion;
        }

        PromocionEntity guardado = promocionCrudRepository.save(promocionEntity);
        return ResponseEntity.ok(guardado);
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
