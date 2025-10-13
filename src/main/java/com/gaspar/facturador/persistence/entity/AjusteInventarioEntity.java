package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ajuste_inventario")
public class AjusteInventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sucursal_item_id", nullable = false)
    private SucursalItemEntity sucursalItem;

    // Cantidad ajustada (puede ser positiva o negativa)
    @Column(nullable = false)
    private Integer cantidadAjuste;

    // Observación opcional
    @Column(length = 500)
    private String observacion;

    // Fecha del ajuste
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    // (Opcional) Usuario que hizo el ajuste
    private String usuario;
}
