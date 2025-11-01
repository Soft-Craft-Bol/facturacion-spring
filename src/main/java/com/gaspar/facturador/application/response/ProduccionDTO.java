package com.gaspar.facturador.application.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProduccionDTO {
    @NotNull
    private Integer recetaId;

    @NotNull
    private Integer sucursalId;

    @NotNull
    private BigDecimal cantidad; // Cantidad de lotes a producir

    private String observaciones;

    // Opcional: Permite sobrescribir los porcentajes de insumos alternativos
    private List<InsumoPorcentajeDTO> porcentajesInsumos;

    @Data
    public static class InsumoPorcentajeDTO {
        @NotNull
        private Long insumoGenericoId;

        @NotNull
        private Long insumoId; // Insumo específico (marca)

        @NotNull
        private BigDecimal porcentaje; // 70.00 (70%)
    }
}

