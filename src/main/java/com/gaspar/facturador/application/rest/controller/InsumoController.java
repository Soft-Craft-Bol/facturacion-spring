package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.InsumoRequest;
import com.gaspar.facturador.application.response.InsumoResponse;
import com.gaspar.facturador.application.response.InsumoSucursalResponse;
import com.gaspar.facturador.domain.service.InsumoService;
import com.gaspar.facturador.persistence.crud.InsumoCrudRepository;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.ItemEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
public class InsumoController {

    private final InsumoService insumoService;

    public InsumoController(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<InsumoResponse> crearInsumo(@Valid @RequestBody InsumoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(insumoService.createInsumo(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponse> obtenerInsumo(@PathVariable Long id) {
        return ResponseEntity.ok(insumoService.getInsumoById(id));
    }

    @GetMapping("/activos/{id}")
    public ResponseEntity<InsumoResponse> obtenerInsumoActivo(@PathVariable Long id) {
        return ResponseEntity.ok(insumoService.getActiveInsumoById(id));
    }

    @GetMapping
    public ResponseEntity<Page<InsumoResponse>> listarInsumos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean activo,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(insumoService.getAllInsumos(nombre, tipo, activo, pageable));
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<Page<InsumoSucursalResponse>> listarInsumosPorSucursal(
            @PathVariable Long sucursalId,
            @RequestParam(defaultValue = "true") boolean soloActivos,
            Pageable pageable) {

        return ResponseEntity.ok(insumoService.getInsumosBySucursal(sucursalId, soloActivos, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponse> actualizarInsumo(
            @PathVariable Long id,
            @Valid @RequestBody InsumoRequest request) {
        return ResponseEntity.ok(insumoService.updateInsumo(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstadoInsumo(
            @PathVariable Long id,
            @RequestParam boolean activo) {
        insumoService.toggleInsumoStatus(id, activo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInsumo(@PathVariable Long id) {
        insumoService.deleteInsumo(id);
        return ResponseEntity.noContent().build();
    }
}