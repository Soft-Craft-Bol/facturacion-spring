package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Getter
@Setter
@Entity
@Table(name = "producto_servicio")
public class ProductoServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String codigoActividad;

    private Long codigoProducto;

    @Column(length = 1000)
    private String descripcionProducto;
}
