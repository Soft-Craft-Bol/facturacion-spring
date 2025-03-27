package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
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
    public ResponseEntity<InsumoEntity> createInsumo(@RequestBody InsumoEntity insumo){
        InsumoEntity createdInsumo = insumoCrudRepository.save(insumo);
        return new ResponseEntity<>(createdInsumo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoEntity> updateInsumo(@PathVariable Long id, @RequestBody InsumoEntity insumoEdited){
        Optional<InsumoEntity> insumo = insumoCrudRepository.findById(id);
        if(insumo.isPresent()){
            InsumoEntity updatedInsumo = insumo.get();
            updatedInsumo.setNombre(insumoEdited.getNombre());
            updatedInsumo.setProveedor(insumoEdited.getProveedor());
            updatedInsumo.setMarca(insumoEdited.getMarca());
            updatedInsumo.setPrecio(insumoEdited.getPrecio());
            updatedInsumo.setDescripcion(insumoEdited.getDescripcion());
            InsumoEntity savedInsumo = insumoCrudRepository.save(updatedInsumo); // Guardar los cambios
            return ResponseEntity.ok(savedInsumo);
        }else{
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

}
