package com.gaspar.facturador.application.rest.controller;


import com.gaspar.facturador.application.request.ProveedorRequest;
import com.gaspar.facturador.application.response.ProveedorResponse;
import com.gaspar.facturador.domain.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService proveedorService;

    @PostMapping("/registrar")
    public ResponseEntity<ProveedorResponse> crearProveedor(
            @Valid @RequestBody ProveedorRequest request) {
        ProveedorResponse response = proveedorService.crearProveedor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtenerProveedorPorId(
            @PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        ProveedorResponse response = proveedorService.obtenerProveedorPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listarTodosLosProveedores() {
        List<ProveedorResponse> proveedores = proveedorService.listarTodosLosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> actualizarProveedor(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequest request) throws ChangeSetPersister.NotFoundException {
        ProveedorResponse response = proveedorService.actualizarProveedor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(
            @PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}