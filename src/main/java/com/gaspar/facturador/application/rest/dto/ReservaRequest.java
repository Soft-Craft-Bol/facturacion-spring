package com.gaspar.facturador.application.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReservaRequest {

    @NotNull
    private Integer idItem;

    @NotNull
    private Integer idPuntoVenta;

    @NotNull
    private Integer idCliente;

    @NotNull
    private BigDecimal cantidad;

    @NotNull
    private BigDecimal anticipo;

    @NotNull
    private BigDecimal saldoPendiente;

    private String observaciones;
}
