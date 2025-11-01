package com.gaspar.facturador.application.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemReservaRequest {

    @NotNull
    private Integer idItem;

    public void setCantidad(@NotNull BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public @NotNull BigDecimal getCantidad() {
        return cantidad;
    }

    @NotNull
    private BigDecimal cantidad;
}