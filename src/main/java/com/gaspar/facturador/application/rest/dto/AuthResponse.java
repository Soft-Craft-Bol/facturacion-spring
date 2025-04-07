package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;

import java.util.Set;

public record AuthResponse(
        String username,
        String message,
        String jwt,
        Boolean status,
        String photo,
        Set<PuntoVentaEntity> puntosVenta) {
}