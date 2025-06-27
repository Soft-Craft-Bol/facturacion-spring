package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.CompraInsumoRequest;
import com.gaspar.facturador.domain.service.CompraInsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras-insumos")
@RequiredArgsConstructor
public class CompraInsumoController {

    private final CompraInsumoService compraInsumoService;

    @PostMapping
    public ResponseEntity<Void> registrarCompraInsumo(
            @Valid @RequestBody CompraInsumoRequest request) {
        try {
            compraInsumoService.registrarCompraInsumo(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}