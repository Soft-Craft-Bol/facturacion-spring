package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cierre_caja_detalle")
public class CierreCajaDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cierre_caja_id", nullable = false)
    private CierreCajasEnity cierreCaja;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoPagoEntity metodoPago;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoFacturado;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoSinFactura;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoContado;
}