package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@Entity
@Table(name = "parametro")
public class ParametroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String codigoClasificador;

    @NotNull
    private String descripcion;

    @NotNull
    private String codigoTipoParametro;
}
