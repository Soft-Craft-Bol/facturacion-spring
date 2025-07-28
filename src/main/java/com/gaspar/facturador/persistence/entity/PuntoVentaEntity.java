package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "punto_venta")
public class PuntoVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private Integer codigo;

    @NotEmpty
    private String nombre;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private SucursalEntity sucursal;

//    @ManyToMany(mappedBy = "puntosVenta")
//    private Set<UserEntity> users = new HashSet<>();
}
