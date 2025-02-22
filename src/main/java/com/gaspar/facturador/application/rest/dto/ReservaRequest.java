package com.gaspar.facturador.application.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReservaRequest {

    @NotNull
    private Integer idPuntoVenta;

    @NotNull
    private Integer idCliente;

    @NotNull
    private List<ItemReservaRequest> items; // Lista de items reservados

    @NotNull
    private BigDecimal anticipo;

    @NotNull
    private BigDecimal saldoPendiente;

    @NotNull
    private String metodoPago; // QR, TARJETA, TRANSFERENCIA

    private String comprobante; // Comprobante de pago (para QR y TRANSFERENCIA)

    private String observaciones;
}