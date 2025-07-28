package com.gaspar.facturador.application.request;

import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class InsumoRequest {
    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String nombre;

    @NotNull(message = "El tipo de insumo es obligatorio")
    private TipoInsumo tipo;

    private BigDecimal precioActual;
    private BigDecimal cantidad;
    private String unidades;
    private String imagen;
}