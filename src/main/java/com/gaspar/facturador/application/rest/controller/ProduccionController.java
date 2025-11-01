package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.ProduccionDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionFilterDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionPageDTO;
import com.gaspar.facturador.application.rest.dto.ProduccionResponseDTO;
import com.gaspar.facturador.domain.service.ProduccionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/produccion")
public class ProduccionController {

    private final ProduccionService produccionService;

    public ProduccionController(ProduccionService produccionService) {
        this.produccionService = produccionService;
    }

    @PostMapping
    public ResponseEntity<String> registrarProduccion(@RequestBody ProduccionDTO produccionDTO) {
        try {
            produccionService.registrarProduccion(produccionDTO);
            return ResponseEntity.ok("Producción registrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduccionResponseDTO> obtenerProduccionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produccionService.obtenerProduccionPorId(id));
    }

    @GetMapping
    public ResponseEntity<ProduccionPageDTO> listarProduccionesPaginadas(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Integer recetaId,
            @RequestParam(required = false) Integer productoId,
            @RequestParam(required = false) Integer sucursalId) {

        ProduccionFilterDTO filtros = new ProduccionFilterDTO();
        filtros.setFechaInicio(fechaInicio);
        filtros.setFechaFin(fechaFin);
        filtros.setRecetaId(recetaId);
        filtros.setProductoId(productoId);
        filtros.setSucursalId(sucursalId);

        return ResponseEntity.ok(produccionService.listarProduccionesPaginadas(pageable, filtros));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProduccion(@PathVariable Long id) {
        try {
            produccionService.eliminarProduccion(id);
            return ResponseEntity.ok("Producción eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
