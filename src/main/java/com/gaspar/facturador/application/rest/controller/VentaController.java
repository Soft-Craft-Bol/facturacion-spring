package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.domain.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    @Autowired
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentasEntity>> getAllVentas() {
        List<VentasEntity> ventas = ventaService.getAllVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasEntity> getVentaById(@PathVariable Long id) {
        Optional<VentasEntity> venta = ventaService.getVentaById(id);
        return venta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<VentasEntity> createVenta(@RequestBody VentasEntity venta) {
        VentasEntity nuevaVenta = ventaService.saveVenta(venta);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tipo-comprobante/{tipoComprobante}")
    public ResponseEntity<List<VentasEntity>> getVentasByTipoComprobante(@PathVariable String tipoComprobante) {
        List<VentasEntity> ventas = ventaService.getVentasByTipoComprobante(tipoComprobante);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
}