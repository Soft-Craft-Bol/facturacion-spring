package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.SucursalInsumoRequest;
import com.gaspar.facturador.application.request.SucursalInsumosMasivoRequest;
import com.gaspar.facturador.domain.service.SucursalInsumoService;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalCrudRepository;
import com.gaspar.facturador.persistence.crud.SucursalInsumoCrudRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/sucursal-insumos")
public class SucursalInsumoController {
    @Autowired
    private SucursalCrudRepository sucursalCrudRepository;
    @Autowired
    private InsumoCrudRepository insumoCrudRepository;
    @Autowired
    private SucursalInsumoCrudRepository sucursalInsumoCrudRepository;

    private final SucursalInsumoService sucursalInsumoService;

    public SucursalInsumoController(SucursalInsumoService sucursalInsumoService) {
        this.sucursalInsumoService = sucursalInsumoService;
    }

    @PostMapping("/{insumoId}")
    public ResponseEntity<Void> agregarInsumoASucursal(
            @PathVariable Long insumoId,
            @Valid @RequestBody SucursalInsumoRequest request) {
        try {
            sucursalInsumoService.addInsumoToSucursal(insumoId, request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{insumoId}/sucursal/{sucursalId}/stock")
    public ResponseEntity<Void> actualizarStockInsumo(
            @PathVariable Long insumoId,
            @PathVariable Integer sucursalId,
            @RequestParam BigDecimal cantidad) {
        try {
            sucursalInsumoService.updateStockInsumo(sucursalId, insumoId, cantidad);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/masivo")
    public ResponseEntity<Void> agregarInsumosMasivoASucursal(
            @Valid @RequestBody SucursalInsumosMasivoRequest request) {
        try {
            sucursalInsumoService.addInsumosMasivoToSucursal(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{insumoId}/sucursal/{sucursalId}/stock-minimo")
    public ResponseEntity<Void> actualizarStockMinimoInsumo(
            @PathVariable Long insumoId,
            @PathVariable Integer sucursalId,
            @RequestParam BigDecimal stockMinimo) {
        try {
            sucursalInsumoService.updateStockMinimo(sucursalId, insumoId, stockMinimo);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
