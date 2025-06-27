package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra_insumo")
public class CompraInsumoEntity {
    @Id @GeneratedValue private Long id;

    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "gasto_id")
    private GastoEntity gasto;

    @ManyToOne
    private InsumoEntity insumo;

    @ManyToOne
    private SucursalEntity sucursal;

    @ManyToOne
    private ProveedorEntity proveedor;
}