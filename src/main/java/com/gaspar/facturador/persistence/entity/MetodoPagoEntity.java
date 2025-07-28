package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "metodos_pago")
public class MetodoPagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // efectivo, tarjeta, transferencia, etc.
    private String descripcion;
    private boolean activo;
}