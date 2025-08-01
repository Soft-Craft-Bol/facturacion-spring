package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CierreCajaRequest {
    private Long cajaId;

    private BigDecimal montoInicial;

    private BigDecimal totalVentas;

    private BigDecimal totalGastos;

    private BigDecimal totalEfectivo;

    private BigDecimal totalTarjeta;
    private BigDecimal totalQr;
    private String observaciones;
}