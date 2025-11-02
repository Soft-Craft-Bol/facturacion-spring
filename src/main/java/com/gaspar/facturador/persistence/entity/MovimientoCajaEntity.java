package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_caja")
@Data
public class MovimientoCajaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CajasEntity caja;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Column(precision = 10, scale = 2)
    private BigDecimal monto;

    private String descripcion;
    private LocalDateTime fecha;
}