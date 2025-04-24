package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.PuntoVentaRepository;
import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.dto.VentaHoyDTO;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import com.gaspar.facturador.domain.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @GetMapping("/hoy")
    public ResponseEntity<Page<VentaHoyDTO>> getVentasDeHoy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VentaHoyDTO> ventas = ventaService.getVentasDeHoy(page, size);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/totales-por-dia")
    public ResponseEntity<List<TotalVentasPorDiaDTO>> obtenerTotalesPorDia() {
        List<TotalVentasPorDiaDTO> totales = ventaService.obtenerTotalesVentasPorDia();
        return ResponseEntity.ok(totales);
    }

}
