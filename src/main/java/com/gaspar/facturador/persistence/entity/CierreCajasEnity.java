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

    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "monto_inicial")
    private BigDecimal montoInicial;

    @Column(name = "monto_final")
    private BigDecimal totalVentas;

    @Column(name = "total_gastos")
    private BigDecimal totalGastos;

    @Column(name = "total_esperados")
    private BigDecimal totalEsperados;

    @Column(name = "total_contados")
    private BigDecimal totalContados;

    @Column(name = "diferencia")
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
