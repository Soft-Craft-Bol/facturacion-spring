package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.CompraInsumoRequest;
import com.gaspar.facturador.application.response.CompraInsumoResponse;
import com.gaspar.facturador.application.response.PagedResponse;
import com.gaspar.facturador.domain.service.CompraInsumoService;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/compras-insumos")
@RequiredArgsConstructor
public class CompraInsumoController {

    private final CompraInsumoService compraInsumoService;

    @PostMapping
    public ResponseEntity<Void> registrarCompraInsumo(
            @Valid @RequestBody CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        compraInsumoService.registrarCompraInsumo(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraInsumoResponse> obtenerCompraPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraInsumoService.obtenerCompraPorId(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CompraInsumoResponse>> listarCompras(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false) Long proveedorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) TipoInsumo tipoInsumo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha,desc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("fecha")));
        PagedResponse<CompraInsumoResponse> response = compraInsumoService
                .listarComprasConFiltros(sucursalId, proveedorId, fechaInicio, fechaFin, tipoInsumo, pageable);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCompra(
            @PathVariable Long id,
            @Valid @RequestBody CompraInsumoRequest request) throws ChangeSetPersister.NotFoundException {
        compraInsumoService.actualizarCompra(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        compraInsumoService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }
}