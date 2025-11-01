package com.gaspar.facturador.application.request;

import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MetodoPagoRequest {

    @NotNull(message = "El método de pago es obligatorio")
    private TipoPagoEnum metodoPago;

    @NotNull(message = "El monto es obligatorio")
    private BigDecimal monto;

    private String referencia;
    private String entidadBancaria;
}