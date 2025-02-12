package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    @Column(length = 500)
    private String descripcion;

    private Integer unidadMedida;

    private BigDecimal precioUnitario;

    private Integer codigoProductoSin;
}
