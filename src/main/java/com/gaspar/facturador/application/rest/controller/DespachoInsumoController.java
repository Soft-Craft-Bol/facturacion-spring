package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.DespachoInsumoRequest;
import com.gaspar.facturador.application.response.DespachoInsumoResponse;
import com.gaspar.facturador.domain.service.DespachoInsumoService;
import com.gaspar.facturador.persistence.entity.DespachoInsumoEntity;
import com.gaspar.facturador.persistence.entity.DespachoInsumoItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/despachos-insumos")
@RequiredArgsConstructor
public class DespachoInsumoController {
    private final DespachoInsumoService despachoInsumoService;

    @GetMapping
    public ResponseEntity<List<DespachoInsumoResponse>> getAll() {
        return ResponseEntity.ok(despachoInsumoService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private DespachoInsumoResponse convertToDto(DespachoInsumoEntity entity) {
        return DespachoInsumoResponse.builder()
                .id(entity.getId())
                .sucursalOrigen(DespachoInsumoResponse.SucursalMinResponse.builder()
                        .id(entity.getSucursalOrigen().getId())
                        .nombre(entity.getSucursalOrigen().getNombre())
                        .direccion(entity.getSucursalOrigen().getDireccion())
                        .build())
                .sucursalDestino(DespachoInsumoResponse.SucursalMinResponse.builder()
                        .id(entity.getSucursalDestino().getId())
                        .nombre(entity.getSucursalDestino().getNombre())
                        .direccion(entity.getSucursalDestino().getDireccion())
                        .build())
                .fechaDespacho(entity.getFechaDespacho())
                .responsable(entity.getResponsable())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado())
                .items(entity.getItems().stream()
                        .map(this::convertItemToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private DespachoInsumoResponse.DespachoInsumoItemResponse convertItemToDto(DespachoInsumoItemEntity item) {
        return DespachoInsumoResponse.DespachoInsumoItemResponse.builder()
                .id(item.getId())
                .insumo(DespachoInsumoResponse.InsumoMinResponse.builder()
                        .id(item.getInsumo().getId())
                        .nombre(item.getInsumo().getNombre())
                        .unidades(item.getInsumo().getUnidades())
                        .build())
                .cantidadEnviada(item.getCantidadEnviada())
                .cantidadRecibida(item.getCantidadRecibida())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoInsumoEntity> getById(@PathVariable Long id) {
        return despachoInsumoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DespachoInsumoEntity> create(@RequestBody DespachoInsumoRequest request) {
        return new ResponseEntity<>(
                despachoInsumoService.save(request),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        despachoInsumoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}