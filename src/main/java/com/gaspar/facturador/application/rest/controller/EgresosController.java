package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.EgresoRequestDTO;
import com.gaspar.facturador.application.response.EgresosResponseByCaja;
import com.gaspar.facturador.persistence.EgresosRepository;
import com.gaspar.facturador.persistence.crud.CajaRepository;
import com.gaspar.facturador.persistence.entity.EgresosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/egresos")
public class EgresosController {
    @Autowired
    private EgresosRepository egresosRepository;
    @Autowired
    private CajaRepository cajasRepository;

    @GetMapping
    public ResponseEntity<List<EgresosResponseByCaja>> getAllEgresos() {
        List<EgresosEntity> egresos = egresosRepository.getAll();

        List<EgresosResponseByCaja> response = egresos.stream()
                .map(e -> EgresosResponseByCaja.builder()
                        .id(e.getId())
                        .fechaDePago(e.getFechaDePago())
                        .descripcion(e.getDescripcion())
                        .gastoEnum(e.getGastoEnum())
                        .monto(e.getMonto())
                        .tipoPagoEnum(e.getTipoPagoEnum())
                        .pagadoA(e.getPagadoA())
                        .numFacturaComprobante(e.getNumFacturaComprobante())
                        .observaciones(e.getObservaciones())
                        .cajaId(e.getCaja().getId())
                        .cajaNombre(e.getCaja().getNombre())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EgresosEntity> getEgresoById(@PathVariable Long id) {
        Optional<EgresosEntity> egreso = egresosRepository.getById(id);
        return egreso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EgresosResponseByCaja> createEgreso(@RequestBody EgresoRequestDTO request) {
        var caja = cajasRepository.findById(request.getCajaId())
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));

        EgresosEntity egreso = new EgresosEntity();
        egreso.setFechaDePago(request.getFechaDePago());
        egreso.setDescripcion(request.getDescripcion());
        egreso.setGastoEnum(request.getGastoEnum());
        egreso.setMonto(request.getMonto());
        egreso.setTipoPagoEnum(request.getTipoPagoEnum());
        egreso.setPagadoA(request.getPagadoA());
        egreso.setNumFacturaComprobante(request.getNumFacturaComprobante());
        egreso.setObservaciones(request.getObservaciones());
        egreso.setCaja(caja);

        EgresosEntity saved = egresosRepository.save(egreso);

        EgresosResponseByCaja response = EgresosResponseByCaja.builder()
                .id(saved.getId())
                .fechaDePago(saved.getFechaDePago())
                .descripcion(saved.getDescripcion())
                .gastoEnum(saved.getGastoEnum())
                .monto(saved.getMonto())
                .tipoPagoEnum(saved.getTipoPagoEnum())
                .pagadoA(saved.getPagadoA())
                .numFacturaComprobante(saved.getNumFacturaComprobante())
                .observaciones(saved.getObservaciones())
                .cajaId(caja.getId())
                .cajaNombre(caja.getNombre())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EgresosEntity> updateEgreso(@PathVariable Long id, @RequestBody EgresosEntity egresoDetails) {
        EgresosEntity updatedEgreso = egresosRepository.update(id, egresoDetails);
        if (updatedEgreso != null) {
            return ResponseEntity.ok(updatedEgreso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEgreso(@PathVariable Long id) {
        egresosRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/caja/{cajaId}")
    public ResponseEntity<List<EgresosResponseByCaja>> getEgresosByCaja(@PathVariable Long cajaId) {
        List<EgresosEntity> egresos = egresosRepository.getEgresosByCaja(cajaId);

        List<EgresosResponseByCaja> response = egresos.stream()
                .map(e -> EgresosResponseByCaja.builder()
                        .id(e.getId())
                        .fechaDePago(e.getFechaDePago())
                        .descripcion(e.getDescripcion())
                        .gastoEnum(e.getGastoEnum())
                        .monto(e.getMonto())
                        .tipoPagoEnum(e.getTipoPagoEnum())
                        .pagadoA(e.getPagadoA())
                        .numFacturaComprobante(e.getNumFacturaComprobante())
                        .observaciones(e.getObservaciones())
                        .cajaId(e.getCaja().getId())
                        .cajaNombre(e.getCaja().getNombre())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}