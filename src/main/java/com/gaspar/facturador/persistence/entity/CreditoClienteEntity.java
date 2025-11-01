package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "credito_cliente")
public class CreditoClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @Column(name = "limite_credito", precision = 10, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "credito_disponible", precision = 10, scale = 2)
    private BigDecimal creditoDisponible;

    @Column(name = "estado")
    private String estado; // ACTIVO, SUSPENDIDO, CANCELADO

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;
}