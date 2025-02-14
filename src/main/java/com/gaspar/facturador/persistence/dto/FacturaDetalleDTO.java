package com.gaspar.facturador.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDetalleDTO {
    private String descripcion;
    private BigDecimal subTotal;
}