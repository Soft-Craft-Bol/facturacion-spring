package com.gaspar.facturador.persistence.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private ItemEntity item;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_punto_venta", nullable = false)
    private PuntoVentaEntity puntoVenta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    @NotNull
    @Column(nullable = false)
    private BigDecimal cantidad;

    @NotNull
    @Column(nullable = false)
    private BigDecimal anticipo;

    @NotNull
    @Column(nullable = false)
    private BigDecimal saldoPendiente;

    @NotNull
    @Column(nullable = false)
    private String estado;

    @Column(length = 500)
    private String observaciones;

}