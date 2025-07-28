package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimientoMermaDTO {
    private Long sucursalId;
    private Long insumoId;
    private Integer itemId;
    private BigDecimal cantidad;
    private String motivo;
    private String registradoPor;
    private String tipo; // MERMA, DONACION, OTRO
}
