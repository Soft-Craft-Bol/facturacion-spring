package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.application.response.SucursalDTO;
import com.gaspar.facturador.persistence.dto.HorarioDTO;
import com.gaspar.facturador.persistence.dto.PuntoVentaDTO;
import io.micrometer.common.lang.Nullable;

import java.util.List;
import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String message,
        String jwt,
        Boolean status,
        String photo,
        @Nullable Set<SucursalUserDTO> sucursal,
        @Nullable Set<PuntoVentaDTO> puntosVenta,
        @Nullable Set<String> permissions,
        @Nullable Set<CajaAbiertaDTO> cajasAbiertas,
        @Nullable List<HorarioDTO> horarios) {
}