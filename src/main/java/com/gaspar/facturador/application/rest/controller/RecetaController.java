package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.RecetaDTO;
import com.gaspar.facturador.domain.service.RecetaService;
import com.gaspar.facturador.persistence.dto.CrearRecetaDTO;
import com.gaspar.facturador.persistence.entity.RecetasEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping
    public ResponseEntity<RecetaDTO> crearReceta(@RequestBody RecetaDTO recetaDTO) {
        RecetasEntity recetaCreada = recetaService.crearReceta(recetaDTO);
        RecetaDTO respuesta = recetaService.convertirADTO(recetaCreada);
        return ResponseEntity.ok(respuesta);
    }
    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listarTodasLasRecetas() {
        List<RecetasEntity> recetas = recetaService.obtenerTodasLasRecetas();
        List<RecetaDTO> response = recetas.stream()
                .map(recetaService::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> obtenerRecetaPorId(@PathVariable Integer id) {
        RecetasEntity receta = recetaService.obtenerRecetaConInsumos(id);
        return ResponseEntity.ok(recetaService.convertirADTO(receta));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<RecetaDTO>> buscarRecetasPorProducto(@PathVariable Integer productoId) {
        List<RecetasEntity> recetas = recetaService.buscarRecetasPorProducto(productoId);
        List<RecetaDTO> response = recetas.stream()
                .map(recetaService::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RecetaDTO>> buscarRecetasPorNombre(@RequestParam String nombre) {
        List<RecetasEntity> recetas = recetaService.buscarRecetasPorNombre(nombre);
        List<RecetaDTO> response = recetas.stream()
                .map(recetaService::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDTO> actualizarReceta(
            @PathVariable Integer id,
            @Valid @RequestBody RecetaDTO recetaDTO) {
        recetaService.actualizarReceta(id, recetaDTO);
        RecetasEntity recetaActualizada = recetaService.obtenerRecetaConInsumos(id);
        return ResponseEntity.ok(recetaService.convertirADTO(recetaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Integer id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

}
