package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "producciones")
@Data
public class ProduccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    private Integer cantidadProducida;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    private RecetasEntity receta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ItemEntity producto;
}
