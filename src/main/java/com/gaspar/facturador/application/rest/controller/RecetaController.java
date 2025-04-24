package com.gaspar.facturador.web.controller;

import com.gaspar.facturador.domain.service.RecetaService;
import com.gaspar.facturador.persistence.dto.CrearRecetaDTO;
import com.gaspar.facturador.persistence.entity.RecetasEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    @PostMapping
    public ResponseEntity<Void> crearReceta(@RequestBody CrearRecetaDTO dto) {
        recetaService.crearReceta(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RecetasEntity>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }
}
