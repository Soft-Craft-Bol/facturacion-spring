package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AbonoRequest {
    @NotNull
    private BigDecimal montoAbono;

    @NotBlank
    private String metodoPago;

    private String referencia;

    @NotNull
    private Long cajaId;

    @NotBlank
    private String usuario;
}