package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import io.micrometer.common.lang.Nullable;

import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String message,
        String jwt,
        Boolean status,
        String photo,
        @Nullable Set<PuntoVentaEntity> puntosVenta) {
}