package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recetas")
@Getter
@Setter
public class RecetasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la receta es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Positive(message = "La cantidad debe ser positiva")
    private Integer cantidadUnidades; // Cantidad total de unidades producidas

    @Positive(message = "El peso unitario debe ser positivo")
    private BigDecimal pesoUnitario; // Peso por unidad en kg

    @NotNull(message = "La fecha de creación es obligatoria")
    private Date fechaCreacion;

    @NotNull(message = "La fecha de actualización es obligatoria")
    private Date fechaActualizacion;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaInsumoEntity> recetaInsumos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ItemEntity producto;

    private BigDecimal rendimiento; // Ej: 1 receta produce 100 unidades
    private String instrucciones;
    private Integer tiempoProduccionMinutos;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = new Date();
        fechaActualizacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = new Date();
    }
}