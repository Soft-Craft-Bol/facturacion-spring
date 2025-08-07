package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.entity.CategoriaEntity;
import com.gaspar.facturador.persistence.crud.CategoriaCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaCrudRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaEntity>> getAllCategorias() {
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaEntity> createCategoria(@RequestBody CategoriaEntity categoria) {
        CategoriaEntity savedCategoria = categoriaRepository.save(categoria);
        return new ResponseEntity<>(savedCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaEntity> updateCategoria(@PathVariable Integer id, @RequestBody CategoriaEntity categoriaDetails) {
        Optional<CategoriaEntity> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            CategoriaEntity updatedCategoria = categoria.get();
            updatedCategoria.setNombre(categoriaDetails.getNombre());
            updatedCategoria.setDescripcion(categoriaDetails.getDescripcion());
            categoriaRepository.save(updatedCategoria);
            return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}