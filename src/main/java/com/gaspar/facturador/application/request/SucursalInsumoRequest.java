package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SucursalInsumoRequest {
    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursalId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private BigDecimal cantidad;

    @Positive(message = "El stock mínimo debe ser mayor a cero")
    private Integer stockMinimo;

    private Date fechaVencimiento;
}