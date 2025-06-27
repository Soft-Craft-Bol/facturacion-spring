package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.CierreCajaRequest;
import com.gaspar.facturador.application.response.CierreCajaResponse;
import com.gaspar.facturador.domain.service.CierreCajaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cierres-caja")
@RequiredArgsConstructor
public class CierreCajaController {

    private final CierreCajaService cierreCajaService;

    @PostMapping("/abrir")
    public ResponseEntity<CierreCajaResponse> abrirCierre(
            @Valid @RequestBody CierreCajaRequest request) throws ChangeSetPersister.NotFoundException {
        CierreCajaResponse response = cierreCajaService.registrarCierre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{cierreId}/cerrar")
    public ResponseEntity<CierreCajaResponse> cerrarCierre(
            @PathVariable Long cierreId,
            @Valid @RequestBody CierreCajaRequest request) throws ChangeSetPersister.NotFoundException {
        CierreCajaResponse response = cierreCajaService.finalizarCierre(cierreId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/caja/{cajaId}")
    public ResponseEntity<List<CierreCajaResponse>> obtenerCierresPorCaja(
            @PathVariable Long cajaId) {
        List<CierreCajaResponse> response = cierreCajaService.obtenerCierresPorCaja(cajaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/caja/{cajaId}/abierto")
    public ResponseEntity<CierreCajaResponse> obtenerCierreAbierto(
            @PathVariable Long cajaId) throws ChangeSetPersister.NotFoundException {
        CierreCajaResponse response = cierreCajaService.obtenerCierreAbierto(cajaId);
        return ResponseEntity.ok(response);
    }
}