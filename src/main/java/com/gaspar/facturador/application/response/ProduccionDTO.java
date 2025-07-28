package com.gaspar.facturador.application.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProduccionDTO {
    @NotNull
    private Integer recetaId;

    @NotNull
    private BigDecimal cantidad;

    @NotNull
    private Integer sucursalId;

    private String observaciones;
}