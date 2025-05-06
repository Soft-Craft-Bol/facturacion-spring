package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.ProduccionDTO;
import com.gaspar.facturador.domain.service.ProduccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produccion")
public class ProduccionController {

    private final ProduccionService produccionService;

    public ProduccionController(ProduccionService produccionService) {
        this.produccionService = produccionService;
    }

    @PostMapping
    public ResponseEntity<String> registrarProduccion(@RequestBody ProduccionDTO produccionDTO) {
        try {
            produccionService.registrarProduccion(produccionDTO);
            return ResponseEntity.ok("Producción registrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
