package com.gaspar.facturador.application.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsumoRecetaDTO {
    @NotNull(message = "El insumo es obligatorio")
    private Long insumoId;

    @Positive(message = "La cantidad debe ser positiva")
    private BigDecimal cantidad;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida;

    // Campos para mostrar en frontend
    private String nombreInsumo;
    private String marcaInsumo;
    private BigDecimal precioUnitario;
}