package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ventas_detalle")
public class VentasDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonBackReference
    private VentasEntity venta;

    @ManyToOne
    @JoinColumn(name="id_producto", nullable = false)
    private ItemEntity producto;
    private BigDecimal cantidad;
    private BigDecimal montoDescuento;
    @Column(name = "descripcion_producto", length = 100)
    private String descripcionProducto;
    private BigDecimal precioUnitario;
}