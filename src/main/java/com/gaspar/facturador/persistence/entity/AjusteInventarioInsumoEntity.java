package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "ajuste_inventario_insumo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AjusteInventarioInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_insumo_id", nullable = false)
    private SucursalInsumoEntity sucursalInsumo;

    @Column(nullable = false)
    private BigDecimal cantidadAnterior;

    @Column(nullable = false)
    private BigDecimal cantidadNueva;

    @Column(nullable = false)
    private BigDecimal diferencia;

    @Column(length = 20, nullable = false)
    private String tipoAjuste; // ENTRADA, SALIDA, AJUSTE

    @Column(length = 500)
    private String motivo;

    @Column(nullable = false)
    private Date fechaAjuste;

    @Column(length = 100, nullable = false)
    private String usuarioResponsable;

    // Campos para auditoría
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = new Date();
        fechaAjuste = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = new Date();
    }
}