package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.CajaRequest;
import com.gaspar.facturador.application.response.CajaResponse;
import com.gaspar.facturador.domain.service.CajaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cajas")
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;

    @PostMapping
    public ResponseEntity<CajaResponse> crearCaja(
            @Valid @RequestBody CajaRequest request) throws ChangeSetPersister.NotFoundException {
        CajaResponse response = cajaService.crearCaja(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<CajaResponse>> obtenerCajasPorSucursal(
            @PathVariable Long sucursalId) {
        List<CajaResponse> response = cajaService.obtenerCajasPorSucursal(sucursalId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CajaResponse> obtenerCajaPorId(
            @PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        CajaResponse response = cajaService.obtenerCajaPorId(id);
        return ResponseEntity.ok(response);
    }
}