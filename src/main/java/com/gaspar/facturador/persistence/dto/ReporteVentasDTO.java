package com.gaspar.facturador.persistence.dto;

import java.math.BigDecimal;

public class ReporteVentasDTO {
    private BigDecimal conFacturaEfectivo;
    private BigDecimal conFacturaQr;
    private BigDecimal conFacturaTransferencia;
    private BigDecimal totalConFactura;

    private BigDecimal sinFacturaEfectivo;
    private BigDecimal sinFacturaQr;
    private BigDecimal sinFacturaTransferencia;
    private BigDecimal totalSinFactura;

    private BigDecimal totalGeneral;

    // Constructor
    public ReporteVentasDTO(
            BigDecimal conFacturaEfectivo, BigDecimal conFacturaQr, BigDecimal conFacturaTransferencia,
            BigDecimal sinFacturaEfectivo, BigDecimal sinFacturaQr, BigDecimal sinFacturaTransferencia
    ) {
        this.conFacturaEfectivo = conFacturaEfectivo;
        this.conFacturaQr = conFacturaQr;
        this.conFacturaTransferencia = conFacturaTransferencia;
        this.totalConFactura = conFacturaEfectivo.add(conFacturaQr).add(conFacturaTransferencia);

        this.sinFacturaEfectivo = sinFacturaEfectivo;
        this.sinFacturaQr = sinFacturaQr;
        this.sinFacturaTransferencia = sinFacturaTransferencia;
        this.totalSinFactura = sinFacturaEfectivo.add(sinFacturaQr).add(sinFacturaTransferencia);

        this.totalGeneral = totalConFactura.add(totalSinFactura);
    }

    // Getters
}