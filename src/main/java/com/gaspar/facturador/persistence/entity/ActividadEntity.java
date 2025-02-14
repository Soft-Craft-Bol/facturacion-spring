package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "actividad")
public class ActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String codigoCaeb;

    @Column(length = 200)
    private String descripcion;

    private String tipoActividad;
}
