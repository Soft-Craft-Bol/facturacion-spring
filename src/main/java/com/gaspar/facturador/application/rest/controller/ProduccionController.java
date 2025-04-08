package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.ProduccionService;
import com.gaspar.facturador.persistence.dto.ProduccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produccion")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @PostMapping
    public ResponseEntity<?> producir(@RequestBody ProduccionDTO dto) {
        produccionService.producir(dto);
        return ResponseEntity.ok("Producción realizada correctamente");
    }
}
