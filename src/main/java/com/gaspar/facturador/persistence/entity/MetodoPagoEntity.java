package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metodos_pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // efectivo, tarjeta, transferencia, etc.
    private String descripcion;
    private boolean activo;
}