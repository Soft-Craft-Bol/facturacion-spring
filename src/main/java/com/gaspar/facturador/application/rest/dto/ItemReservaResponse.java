package com.gaspar.facturador.application.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemReservaResponse {

    private Integer idItem;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;

    public ItemReservaResponse( Integer idItem, String descripcion, BigDecimal cantidad, BigDecimal precioUnitario) {
        this.idItem = idItem;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
}