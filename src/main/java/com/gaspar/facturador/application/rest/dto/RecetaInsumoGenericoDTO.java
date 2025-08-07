package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecetaInsumoGenericoDTO {
    private Long insumoGenericoId;
    private BigDecimal cantidad;
    private String unidadMedida;
    private BigDecimal costo;
}