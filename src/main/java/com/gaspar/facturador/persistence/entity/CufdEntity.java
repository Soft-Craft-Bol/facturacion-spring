package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "cufd")
public class CufdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String codigoControl;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaVigencia;
    private Boolean vigente;

    @ManyToOne
    @JoinColumn(name = "id_punto_venta", nullable = false)
    private PuntoVentaEntity puntoVenta;
}
