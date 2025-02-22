package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.ReservaRequest;
import com.gaspar.facturador.application.rest.dto.ReservaResponse;
import com.gaspar.facturador.domain.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.crearReserva(request));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<ReservaResponse>> obtenerReservasNoVerificadas() {
        return ResponseEntity.ok(reservaService.obtenerReservasNoVerificadas());
    }

    @PutMapping("/verificar/{idReserva}")
    public ResponseEntity<String> verificarReserva(@PathVariable Integer idReserva) {
        return ResponseEntity.ok(reservaService.verificarReserva(idReserva));
    }
}