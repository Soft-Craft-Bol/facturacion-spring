package com.gaspar.facturador.application.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CierreCajaResponse {
    private Long id;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private BigDecimal montoInicial;
    private BigDecimal totalVentas;
    private BigDecimal totalGastos;
    private BigDecimal totalEsperado;
    private BigDecimal totalContado;
    private BigDecimal diferencia;
    private BigDecimal totalEfectivo;
    private BigDecimal totalTarjeta;
    private BigDecimal totalQr;
    private String observaciones;
    private Long cajaId;
    private String nombreCaja;
}