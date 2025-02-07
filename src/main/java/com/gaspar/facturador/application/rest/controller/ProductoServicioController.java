package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.domain.service.ProductoServicioService;
import com.gaspar.facturador.persistence.ProductoServicioRepository;
import com.gaspar.facturador.persistence.entity.ProductoServicioEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos-servicios")
public class ProductoServicioController {

    private final ProductoServicioRepository productoServicioRepository;
    public ProductoServicioController(ProductoServicioRepository productoServicioRepository) {
        this.productoServicioRepository = productoServicioRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductoServicioEntity>> getAllProductosServicios() {
        List<ProductoServicioEntity> productosServicios = productoServicioRepository.findAll();
        return new ResponseEntity<>(productosServicios, HttpStatus.OK);
    }
}