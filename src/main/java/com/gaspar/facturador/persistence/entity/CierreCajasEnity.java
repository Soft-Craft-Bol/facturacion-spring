package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cierre_cajas")
public class CierreCajasEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "caja_id", nullable = false)
    private CajasEntity caja;

    // Gastos
    @Column(precision = 10, scale = 2)
    private BigDecimal totalGastos;

    // Conteo final
    @Column(precision = 10, scale = 2)
    private BigDecimal efectivoContado;

    @Column(precision = 10, scale = 2)
    private BigDecimal tarjetaContado;

    @Column(precision = 10, scale = 2)
    private BigDecimal qrContado;

    // Calculados
    @Column(precision = 10, scale = 2)
    private BigDecimal totalVentas;

    @Column(precision = 10, scale = 2)
    private BigDecimal diferencia;

    private String observaciones;

    @OneToMany(mappedBy = "cierreCaja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CierreCajaDetalleEntity> detalles = new ArrayList<>();

    public void agregarDetalle(CierreCajaDetalleEntity detalle) {
        detalle.setCierreCaja(this);
        this.detalles.add(detalle);
    }
}