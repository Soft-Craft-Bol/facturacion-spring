package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "factura_detalle")
public class FacturaDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actividadEconomica;
    private Integer codigoProductoSin;
    private String codigoProducto;
    @Column(length = 500)
    private String descripcion;
    private BigDecimal cantidad;
    private Integer unidadMedida;
    private BigDecimal precioUnitario;
    private BigDecimal montoDescuento;
    private BigDecimal subTotal;
    private String numeroSerie;
    private String numeroImei;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_factura", referencedColumnName = "id")
    private FacturaEntity factura;
}
