package com.gaspar.facturador.application.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class SucursalInsumosMasivoRequest {
    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursalId;

    @Valid
    @NotNull(message = "La lista de insumos es obligatoria")
    private List<InsumoAsignacionRequest> insumos;

    @Data
    public static class InsumoAsignacionRequest {
        @NotNull(message = "El ID del insumo es obligatorio")
        private Long insumoId;

        private BigDecimal cantidad;
        private BigDecimal stockMinimo;
        private Date fechaVencimiento;
    }
}

