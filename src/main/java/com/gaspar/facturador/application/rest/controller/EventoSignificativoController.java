package com.gaspar.facturador.application.rest.controller;

import bo.gob.impuestos.siat.api.facturacion.operaciones.RespuestaListaEventos;
import com.gaspar.facturador.application.rest.dto.EventoSignificativoRequest;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.domain.service.EventoSignificativoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento-significativo")
public class EventoSignificativoController {

    private final EventoSignificativoService eventoSignificativoService;

    public EventoSignificativoController(EventoSignificativoService eventoSignificativoService) {
        this.eventoSignificativoService = eventoSignificativoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<RespuestaListaEventos> registrarEventoSignificativo(
            @RequestBody EventoSignificativoRequest request
    ) {
        try {
            RespuestaListaEventos respuesta = eventoSignificativoService.registrarEventoSignificativo(
                    request.getIdPuntoVenta(),
                    request.getDescripcion(),
                    request.getCodigoMotivoEvento(),
                    request.getCufdEvento(),
                    request.getFechaHoraInicioEvento(),
                    request.getFechaHoraFinEvento()
            );
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            throw new ProcessException("Error al registrar el evento significativo: " + e.getMessage());
        }
    }
}
