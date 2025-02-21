package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class VentaDetalleRequest {

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private BigDecimal cantidad;

    @NotNull(message = "El monto de descuento es obligatorio")
    @Min(value = 0, message = "El descuento no puede ser negativo")
    private BigDecimal montoDescuento;
}
