package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "empresa")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Long nit;

    @NotEmpty
    private String razonSocial;
}
