package com.gaspar.facturador.application.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class VentaDetalleRequest {

    @NotNull
    private Integer idProducto;

    @NotNull
    private BigDecimal cantidad;

    @NotNull
    private BigDecimal montoDescuento;
}
