package com.gaspar.facturador.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalVentasPorDiaDTO {
    private String fecha; // formato yyyy-MM-dd
    private BigDecimal total;
}