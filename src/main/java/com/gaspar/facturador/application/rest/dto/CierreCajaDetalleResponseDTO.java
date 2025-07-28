package com.gaspar.facturador.application.rest.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CierreCajaDetalleResponseDTO {
    private String metodoPago;
    private BigDecimal montoFacturado;
    private BigDecimal montoSinFactura;
    private BigDecimal montoContado;
}
