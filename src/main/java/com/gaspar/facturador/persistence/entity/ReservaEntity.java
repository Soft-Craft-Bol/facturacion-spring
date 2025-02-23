package com.gaspar.facturador.persistence.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private BigDecimal anticipo;

    @NotNull
    @Column(nullable = false)
    private BigDecimal saldoPendiente;

    @NotNull
    @Column(nullable = false)
    private String estado;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, name = "metodo_pago")
    private String metodoPago;

    private String comprobante;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaItemEntity> items;
}