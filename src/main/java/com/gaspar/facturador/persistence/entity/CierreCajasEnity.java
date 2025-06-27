package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cierre_cajas")
public class CierreCajasEnity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDateTime fechaCierre;

    @Column(name = "monto_inicial", nullable = false)
    private BigDecimal montoInicial;

    @Column(name = "monto_final", nullable = false)
    private BigDecimal totalVentas;

    @Column(name = "total_gastos", nullable = false)
    private BigDecimal totalGastos;

    @Column(name = "total_esperados", nullable = false)
    private BigDecimal totalEsperados;

    @Column(name = "total_contados", nullable = false)
    private BigDecimal totalContados;

    @Column(name = "diferencia", nullable = false)
    private BigDecimal diferencia;

    @Column(name = "total_efectivo")
    private BigDecimal totalEfectivo;

    @Column(name = "total_tarjeta")
    private BigDecimal totalTarjeta;

    @Column(name = "total_qr")
    private BigDecimal totalQr;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "caja_id", nullable = false)
    private CajasEntity caja;
}
