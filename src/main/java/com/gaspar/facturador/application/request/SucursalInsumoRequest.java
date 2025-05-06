package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SucursalInsumoRequest {
    private Integer sucursalId;
    private Long insumoId;
    private BigDecimal cantidad;

    @NotNull(message = "El stock mínimo es requerido")
    private Integer stockMinimo;

    @NotNull(message = "La fecha de ingreso es requerida")
    private Date fechaIngreso;

    @NotNull(message = "La fecha de vencimiento es requerida")
    private Date fechaVencimiento;

    @NotNull(message = "La fecha de última adquisición es requerida")
    private Date ultimaAdquisicion;
}