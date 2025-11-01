package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.MovimientoMermaResponse;
import com.gaspar.facturador.application.rest.dto.MovimientoMermaDTO;
import com.gaspar.facturador.domain.service.MovimientoMermaService;
import com.gaspar.facturador.persistence.entity.enums.TipoMerma;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/mermas")
@RequiredArgsConstructor
public class MovimientoMermaController {

    private final MovimientoMermaService movimientoMermaService;

    @PostMapping
    public ResponseEntity<String> registrarMerma(@RequestBody MovimientoMermaDTO dto) {
        try {
            movimientoMermaService.registrarMerma(dto);
            return ResponseEntity.ok("Merma/Donación registrada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<Page<MovimientoMermaResponse>> listarMermas(
            @RequestParam(required = false) Integer sucursalId,
            @RequestParam(required = false) TipoMerma tipo,
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Pageable pageable
    ) {
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(23, 59, 59) : null;

        var result = movimientoMermaService.buscarMermas(sucursalId, tipo, motivo, inicio, fin, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoMermaResponse> obtenerMermaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoMermaService.obtenerMermaPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMerma(@PathVariable Long id) {
        try {
            movimientoMermaService.eliminarMerma(id);
            return ResponseEntity.ok("Merma eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
