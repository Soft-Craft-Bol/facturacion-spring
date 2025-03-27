package com.gaspar.facturador.persistence.mapper;

import java.time.LocalDateTime;

public interface CufdProjection {
    Integer getId();
    String getCodigo();
    LocalDateTime getFechaInicio();
    LocalDateTime getFechaVigencia();
    Integer getIdPuntoVenta();
}