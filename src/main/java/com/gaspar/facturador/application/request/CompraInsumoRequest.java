package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CompraInsumoRequest {
    @NotNull(message = "El ID del insumo es obligatorio")
    private Long insumoId;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursalId;

    private Long proveedorId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Double cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a cero")
    private BigDecimal precioUnitario;

    private String numeroFactura;
    private String notas;
}