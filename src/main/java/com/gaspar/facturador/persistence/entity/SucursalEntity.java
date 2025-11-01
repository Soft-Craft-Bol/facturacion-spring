package com.gaspar.facturador.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(length = 1024)
    private String image;

    @NotNull
    @Column(precision = 10, scale = 8)
    private BigDecimal latitud;

    @NotNull
    @Column(precision = 11, scale = 8)
    private BigDecimal longitud;

    public SucursalEntity(Integer idSucursal){
        this.id = idSucursal;
    }
}