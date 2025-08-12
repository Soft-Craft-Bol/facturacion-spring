package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "despacho_insumo_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespachoInsumoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "despacho_id", nullable = false)
    private DespachoInsumoEntity despacho;

    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private InsumoEntity insumo;

    @Column(nullable = false)
    private BigDecimal cantidadEnviada;

    private BigDecimal cantidadRecibida;

    private String observaciones;
}