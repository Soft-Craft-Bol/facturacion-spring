package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_produccion")
@Data
public class MovimientoProduccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private RecetasEntity receta;

    private Long sucursalId;

    private Integer cantidad; // Cantidad de lotes producidos
    private Integer unidadesProduccidas; // Cantidad total de unidades

    private LocalDateTime fechaProduccion;
    private String observaciones;
}