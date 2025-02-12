package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@Entity
@Table(name = "sucursal")
public class SucursalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer codigo;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String departamento;

    @NotEmpty
    private String municipio;

    @NotEmpty
    private String direccion;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "varchar(255) default '0'")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private EmpresaEntity empresa;
}
