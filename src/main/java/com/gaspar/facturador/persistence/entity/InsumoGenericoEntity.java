package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "insumo_generico")
@Data
public class InsumoGenericoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String unidadMedida;

    private String descripcion;

    @OneToMany(mappedBy = "insumoGenerico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsumoGenericoDetalleEntity> insumosAsociados = new ArrayList<>();

}
