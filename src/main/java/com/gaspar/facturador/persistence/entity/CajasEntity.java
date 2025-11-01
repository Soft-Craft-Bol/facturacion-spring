package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.TurnoTrabajo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "cajas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CajasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Estado actual de la caja: ABIERTA, CERRADA o BLOQUEADA.
     */
    @Column(nullable = false, length = 20)
    private String estado;

    /**
     * Turno asignado a la caja: MAÑANA, TARDE, NOCHE.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TurnoTrabajo turno;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montoInicial;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private SucursalEntity sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_apertura_id")
    private UserEntity usuarioApertura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cierre_id")
    private UserEntity usuarioCierre;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

}
