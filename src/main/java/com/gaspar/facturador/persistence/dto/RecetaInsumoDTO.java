package com.gaspar.facturador.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecetaInsumoDTO {
    private Long insumoId;
    private BigDecimal cantidad;
}
