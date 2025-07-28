package com.gaspar.facturador.application.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CierreCajaResponseDTO {
    private BigDecimal efectivoContado;
    private BigDecimal tarjetaContado;
    private BigDecimal qrContado;
    private BigDecimal totalVentas;
    private BigDecimal totalGastos;
    private BigDecimal diferencia;
    private String observaciones;
    private List<CierreCajaDetalleResponseDTO> detalles;
}
