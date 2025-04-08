package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "receta_insumo")
@Data
public class RecetaInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    @JsonIgnore
    private RecetasEntity receta;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private InsumoEntity insumo;

    private BigDecimal cantidad; // Ej: 300 gramos

}
