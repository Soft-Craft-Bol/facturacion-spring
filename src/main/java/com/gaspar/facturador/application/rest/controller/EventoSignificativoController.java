package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.EventoSignificativoDTO;
import com.gaspar.facturador.application.response.EventoSignificativoRegistroResponse;
import com.gaspar.facturador.application.rest.dto.EventoSignificativoRequest;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.domain.service.EventoSignificativoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos-significativos")
public class EventoSignificativoController {

    private final EventoSignificativoService eventoService;

    public EventoSignificativoController(EventoSignificativoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoSignificativoRegistroResponse> registrarEvento(
            @RequestBody EventoSignificativoRequest request) {
        try {
            EventoSignificativoRegistroResponse response = eventoService.registrarEvento(
                    request.getIdPuntoVenta(),
                    request.getCodigoMotivoEvento(),
                    request.getCufdEvento(),
                    request.getFechaHoraInicioEvento(),
                    request.getFechaHoraFinEvento());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ProcessException("Error al registrar evento: " + e.getMessage());
        }
    }

    @GetMapping("/vigentes/{puntoVentaId}")
    public ResponseEntity<List<EventoSignificativoDTO>> obtenerEventosVigentes(
            @PathVariable Integer puntoVentaId) {
        try {
            List<EventoSignificativoDTO> eventos = eventoService.obtenerEventosVigentes(puntoVentaId);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            throw new ProcessException("Error al obtener eventos: " + e.getMessage());
        }
    }

}