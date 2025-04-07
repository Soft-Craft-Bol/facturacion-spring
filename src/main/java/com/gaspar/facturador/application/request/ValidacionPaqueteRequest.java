package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacionPaqueteRequest {
    @NotNull
    private Integer idPuntoVenta;

    @NotBlank(message = "El código de recepción es requerido")
    private String codigoRecepcion;
}