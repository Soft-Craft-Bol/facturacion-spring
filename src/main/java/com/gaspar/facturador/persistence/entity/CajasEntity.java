package com.gaspar.facturador.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cajas")
public class CajasEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private SucursalEntity sucursal;

}
