package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.InsumoGenericoDTO;
import com.gaspar.facturador.application.rest.dto.InsumoGenericoDetalleDTO;
import com.gaspar.facturador.domain.service.InsumoGenericoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insumos-genericos")
@RequiredArgsConstructor
public class InsumoGenericoController {

    private final InsumoGenericoService insumoGenericoService;

    @PostMapping
    public ResponseEntity<InsumoGenericoDTO> crearInsumoGenerico(@RequestBody InsumoGenericoDTO dto) {
        return ResponseEntity.ok(insumoGenericoService.crearInsumoGenerico(dto));
    }

    @GetMapping
    public ResponseEntity<Page<InsumoGenericoDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nombre
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(insumoGenericoService.listarTodosPaginado(nombre, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoGenericoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(insumoGenericoService.obtenerPorId(id));
    }

    @PostMapping("/{id}/asignar-insumo")
    public ResponseEntity<InsumoGenericoDTO> asignarInsumo(
            @PathVariable Long id,
            @RequestBody InsumoGenericoDetalleDTO detalleDTO) {
        return ResponseEntity.ok(insumoGenericoService.asignarInsumo(id, detalleDTO));
    }

    @PostMapping("/{id}/asignar-insumos")
    public ResponseEntity<InsumoGenericoDTO> asignarInsumos(
            @PathVariable Long id,
            @RequestBody List<InsumoGenericoDetalleDTO> detalles) {
        return ResponseEntity.ok(insumoGenericoService.asignarInsumos(id, detalles));
    }


    @DeleteMapping("/{id}/remover-insumo/{insumoId}")
    public ResponseEntity<Void> removerInsumo(
            @PathVariable Long id,
            @PathVariable Long insumoId) {
        insumoGenericoService.removerInsumo(id, insumoId);
        return ResponseEntity.noContent().build();
    }
}