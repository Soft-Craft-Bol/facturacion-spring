package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "venta_pagos")
public class VentaPagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonBackReference
    private VentasEntity venta;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private TipoPagoEnum metodoPago;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "referencia", length = 100)
    private String referencia; // Para números de tarjeta, transferencia, etc.

    @Column(name = "entidad_bancaria", length = 100)
    private String entidadBancaria; // Nombre del banco o entidad
}