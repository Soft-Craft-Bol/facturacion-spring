package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.ReservaRequest;
import com.gaspar.facturador.application.rest.dto.ReservaResponse;
import com.gaspar.facturador.domain.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.crearReserva(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarReservas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reservaService.filtrarReservas(estado, desde, hasta));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<ReservaResponse>> obtenerReservasPendientes() {
        return ResponseEntity.ok(reservaService.filtrarReservas("Pendiente", null, null));
    }

    @PutMapping("/{idReserva}/estado")
    public ResponseEntity<ReservaResponse> cambiarEstadoReserva(
            @PathVariable Integer idReserva,
            @RequestParam String estado,
            @RequestParam(required = false) String motivo) {
        return ResponseEntity.ok(reservaService.cambiarEstadoReserva(idReserva, estado, motivo));
    }
}