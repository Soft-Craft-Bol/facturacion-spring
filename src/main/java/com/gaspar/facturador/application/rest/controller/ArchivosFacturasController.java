package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.response.ArchivoFacturaResponse;
import com.gaspar.facturador.domain.service.ExploradorArchivosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archivos-facturas")
public class ArchivosFacturasController {

    private final ExploradorArchivosService exploradorArchivosService;

    public ArchivosFacturasController(ExploradorArchivosService exploradorArchivosService) {
        this.exploradorArchivosService = exploradorArchivosService;
    }

    @GetMapping
    public ResponseEntity<List<ArchivoFacturaResponse>> obtenerEstructuraArchivos() {
        List<ArchivoFacturaResponse> estructura = exploradorArchivosService.explorarDirectorioFacturas();
        return ResponseEntity.ok(estructura);
    }

    @GetMapping("/descargar")
    public ResponseEntity<byte[]> descargarArchivo(@RequestParam String ruta) {
        // Implementar lógica para descargar el archivo
        // Esto requeriría otro servicio para leer y servir el archivo
        return ResponseEntity.notFound().build();
    }
}