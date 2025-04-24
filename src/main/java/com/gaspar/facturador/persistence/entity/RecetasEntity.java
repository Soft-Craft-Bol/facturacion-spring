package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "recetas")
@Getter
@Setter
public class RecetasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Integer cantidadUnidades; // Ej: 864 unidades

    private String peso; // Opcional


    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaInsumoEntity> recetaInsumos;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ItemEntity producto;

}
