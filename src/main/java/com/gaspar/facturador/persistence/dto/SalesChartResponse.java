package com.gaspar.facturador.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesChartResponse {
    private String day;
    private double ventas;
    private double gastos;
}