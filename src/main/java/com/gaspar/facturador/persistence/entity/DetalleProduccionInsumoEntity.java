package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_produccion_insumo")
@Data
public class DetalleProduccionInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movimiento_produccion_id", nullable = false)
    private MovimientoProduccionEntity movimientoProduccion;

    @ManyToOne
    @JoinColumn(name = "sucursal_insumo_id", nullable = false)
    private SucursalInsumoEntity sucursalInsumo;

    @Column(nullable = false)
    private BigDecimal cantidadUsada;

    private String observaciones; // opcional: registrar marca, lote, motivo
}
