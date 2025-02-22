package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "reserva_item")
@AllArgsConstructor
@NoArgsConstructor
public class ReservaItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private ReservaEntity reserva;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private ItemEntity item;

    @NotNull
    @Column(nullable = false)
    private BigDecimal cantidad;
}
