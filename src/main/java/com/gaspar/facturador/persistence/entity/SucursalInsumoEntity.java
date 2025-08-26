package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "sucursal_insumo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SucursalInsumoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private InsumoEntity insumo;

    @Column(nullable = false)
    private BigDecimal cantidad;

    private Integer stockMinimo;

    private Date fechaIngreso;
    private Date fechaVencimiento;
}
