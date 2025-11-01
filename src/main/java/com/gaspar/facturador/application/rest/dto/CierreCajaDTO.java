package com.gaspar.facturador.application.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CierreCajaDTO {
    private Long cajaId;
    private Integer puntoVenta;
    private Long usuarioId;
    private BigDecimal efectivoFinal;
    private BigDecimal billeteraMovilFinal;
    private BigDecimal transferenciaFinal;
    private BigDecimal gastos;
    private BigDecimal totalSistema;
    private BigDecimal diferencia;
    private String observaciones;
    private Map<String, Map<String, BigDecimal>> resumenPagos;
}
