package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "cuenta_por_cobrar")
public class CuentaPorCobrarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private VentasEntity venta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @Column(name = "monto_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "saldo_pendiente", precision = 10, scale = 2, nullable = false)
    private BigDecimal saldoPendiente;

    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "estado")
    private String estado; // PENDIENTE, PARCIAL, CANCELADO, VENCIDO

    @Column(name = "dias_credito")
    private Integer diasCredito;

    @OneToMany(mappedBy = "cuentaPorCobrar", cascade = CascadeType.ALL)
    private List<AbonoCreditoEntity> abonos;
}