package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.domain.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final PuntoVentaRepository puntoVentaRepository;

    public VentaController(VentaService ventaService, PuntoVentaRepository puntoVentaRepository) {
        this.ventaService = ventaService;
        this.puntoVentaRepository = puntoVentaRepository;
    }

    @GetMapping
    public ResponseEntity<List<VentasEntity>> getAllVentas() {
        List<VentasEntity> ventas = ventaService.getAllVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasEntity> getVentaById(@PathVariable Long id) {
        return ventaService.getVentaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createVenta(@Valid @RequestBody VentaSinFacturaRequest ventaRequest) {
        // Verificar si el punto de venta existe
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(ventaRequest.getIdPuntoVenta()));
        if (puntoVenta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El punto de venta con ID " + ventaRequest.getIdPuntoVenta() + " no existe.");
        }

        try {
            VentasEntity nuevaVenta = ventaService.saveVenta(ventaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la venta: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo-comprobante/{tipoComprobante}")
    public ResponseEntity<List<VentasEntity>> getVentasByTipoComprobante(@PathVariable String tipoComprobante) {
        List<VentasEntity> ventas = ventaService.getVentasByTipoComprobante(tipoComprobante);
        return ResponseEntity.ok(ventas);
    }
}
