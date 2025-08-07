package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "insumo_generico_detalle")
@Data
public class InsumoGenericoDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insumo_generico_id", nullable = false)
    private InsumoGenericoEntity insumoGenerico;

    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private InsumoEntity insumo;

    private Integer prioridad = 1; // opcional, por si quieres ordenar preferencia
}
