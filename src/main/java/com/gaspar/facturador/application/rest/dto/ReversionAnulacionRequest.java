package com.gaspar.facturador.application.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReversionAnulacionRequest {
    @NotNull(message = "El ID del punto de venta no puede ser nulo")
    private Long idPuntoVenta;

    @NotBlank(message = "El CUF no puede estar vacío")
    private String cuf;
}
