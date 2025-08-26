package com.gaspar.facturador.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemReservaResponse {

    private Integer idItem;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private String photoUrl;
    private boolean stockSuficiente;     // ✅ faltaba
    private BigDecimal stockDisponible;
}