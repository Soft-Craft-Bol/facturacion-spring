package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CierreCajaRequest {
    @NotNull(message = "El ID de caja es obligatorio")
    private Long cajaId;

    @NotNull(message = "El monto inicial es obligatorio")
    private BigDecimal montoInicial;

    @NotNull(message = "El total de ventas es obligatorio")
    private BigDecimal totalVentas;

    @NotNull(message = "El total de gastos es obligatorio")
    private BigDecimal totalGastos;

    @NotNull(message = "El total de efectivo es obligatorio")
    private BigDecimal totalEfectivo;

    private BigDecimal totalTarjeta;
    private BigDecimal totalQr;
    private String observaciones;
}