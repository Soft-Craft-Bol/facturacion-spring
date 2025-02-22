package com.gaspar.facturador.application.rest.controller;


import com.gaspar.facturador.application.rest.dto.ReservaRequest;
import com.gaspar.facturador.application.rest.dto.ReservaResponse;
import com.gaspar.facturador.domain.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaRequest request) {
        ReservaResponse response = reservaService.crearReserva(request);
        return ResponseEntity.ok(response);
    }
}
