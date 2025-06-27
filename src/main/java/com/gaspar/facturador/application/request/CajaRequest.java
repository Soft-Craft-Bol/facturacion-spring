package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CajaRequest {
    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El ID de sucursal es obligatorio")
    private Long sucursalId;
}