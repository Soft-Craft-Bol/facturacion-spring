package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "receta_insumo")
@Getter
@Setter
public class RecetaInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    @NotNull(message = "La receta es obligatoria")
    private RecetasEntity receta;

    @ManyToOne
    @JoinColumn(name = "insumo_generico_id", nullable = false)
    private InsumoGenericoEntity insumoGenerico;

    @Positive(message = "La cantidad debe ser positiva")
    private BigDecimal cantidad;

    @Column(length = 20)
    private String unidadMedida; // kg, g, l, ml, etc.

    @Positive(message = "El costo debe ser positivo")
    private BigDecimal costo;
}