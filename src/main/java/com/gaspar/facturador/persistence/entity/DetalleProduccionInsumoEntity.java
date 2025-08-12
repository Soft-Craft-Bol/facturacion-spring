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
    @JoinColumn(name = "produccion_id", nullable = false)
    private ProduccionEntity produccion;

    @ManyToOne
    @JoinColumn(name = "insumo_generico_id")
    private InsumoGenericoEntity insumoGenerico; // Para referencia

    @ManyToOne
    @JoinColumn(name = "sucursal_insumo_id", nullable = false)
    private SucursalInsumoEntity sucursalInsumo; // Stock específico consumido

    @Column(nullable = false)
    private BigDecimal cantidadUsada;

    @Column(precision = 5, scale = 2)
    private BigDecimal porcentajeUsado; // Ej: 70.00 (70% del total requerido)

    private String lote; // Opcional (trazabilidad)
}