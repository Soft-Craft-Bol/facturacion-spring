package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromocionInfo {
    private Long promocionId;
    private BigDecimal precioPromocional;
    private Double porcentajeDescuento;


}