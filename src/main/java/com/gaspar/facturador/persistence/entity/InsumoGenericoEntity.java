package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @PrePersist
    @PreUpdate
    private void validarPrioridadesUnicas() {
        Set<Integer> prioridades = new HashSet<>();
        for (InsumoGenericoDetalleEntity detalle : insumosAsociados) {
            if (!prioridades.add(detalle.getPrioridad())) {
                throw new IllegalStateException("Prioridad duplicada: " + detalle.getPrioridad());
            }
        }
    }
}
