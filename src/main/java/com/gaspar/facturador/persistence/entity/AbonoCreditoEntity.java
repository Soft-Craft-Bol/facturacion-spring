package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "abono_credito")
public class AbonoCreditoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaPorCobrarEntity cuentaPorCobrar;

    @Column(name = "monto_abono", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoAbono;

    @Column(name = "fecha_abono", nullable = false)
    private Date fechaAbono;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "referencia", length = 100)
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "caja_id")
    private CajasEntity caja;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity usuario;
}