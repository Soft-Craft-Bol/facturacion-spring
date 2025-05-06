package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
public class InsumoController {
    @Autowired
    private InsumoCrudRepository insumoCrudRepository;

    public InsumoController(InsumoCrudRepository insumoCrudRepository){
        this.insumoCrudRepository=insumoCrudRepository;
    }

    @GetMapping
    public ResponseEntity<List<InsumoEntity>> getAllInsumos(){
//        List<InsumoEntity> insumos = (List<InsumoEntity>) insumoCrudRepository.findAll();
//        return ResponseEntity.ok(insumos);
        if( insumoCrudRepository.count() > 0){
            return ResponseEntity.ok((List<InsumoEntity>) insumoCrudRepository.findAll());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoEntity> getItemById(@PathVariable long id){
        Optional<InsumoEntity> insumo = insumoCrudRepository.findById(id);
        return insumo
                .map
                        (value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<InsumoEntity> createInsumo(@Valid @RequestBody InsumoEntity insumo) {
        InsumoEntity createdInsumo = insumoCrudRepository.save(insumo);
        return new ResponseEntity<>(createdInsumo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoEntity> updateInsumo(@PathVariable Long id, @RequestBody InsumoEntity insumoEdited) {
        Optional<InsumoEntity> insumoOptional = insumoCrudRepository.findById(id);

        if (insumoOptional.isPresent()) {
            InsumoEntity insumoActual = insumoOptional.get();

            // Copiar propiedades básicas excepto la colección
            insumoActual.setNombre(insumoEdited.getNombre());
            insumoActual.setMarca(insumoEdited.getMarca());
            insumoActual.setPrecio(insumoEdited.getPrecio());
            insumoActual.setUnidades(insumoEdited.getUnidades());
            insumoActual.setDescripcion(insumoEdited.getDescripcion());
            insumoActual.setImagen(insumoEdited.getImagen());
            insumoActual.setProveedor(insumoEdited.getProveedor());

            // ⚠️ Actualiza sucursalInsumo de forma segura
            if (insumoEdited.getSucursalInsumo() != null) {
                insumoActual.getSucursalInsumo().clear();
                for (var sucursal : insumoEdited.getSucursalInsumo()) {
                    sucursal.setInsumo(insumoActual); // mantener la relación bidireccional
                    insumoActual.getSucursalInsumo().add(sucursal);
                }
            }

            InsumoEntity saved = insumoCrudRepository.save(insumoActual);
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteInsumo(@PathVariable Long id){
        try {
            if (insumoCrudRepository.existsById(id)){
                insumoCrudRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<InsumoEntity>> getActivos() {
        return ResponseEntity.ok(insumoCrudRepository.findByActivoTrue());
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<InsumoEntity>> getInactivos() {
        return ResponseEntity.ok(insumoCrudRepository.findByActivoFalse());
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarInsumo(@PathVariable Long id){
        Optional<InsumoEntity> insumo = insumoCrudRepository.findById(id);
        if(insumo.isPresent()){
            InsumoEntity i = insumo.get();
            i.setActivo(false);
            insumoCrudRepository.save(i);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Void> activarInsumo(@PathVariable Long id) {
        Optional<InsumoEntity> insumo = insumoCrudRepository.findById(id);
        if(insumo.isPresent()) {
            InsumoEntity i = insumo.get();
            i.setActivo(true);
            insumoCrudRepository.save(i);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
