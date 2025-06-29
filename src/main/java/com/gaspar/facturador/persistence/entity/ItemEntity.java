package com.gaspar.facturador.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, columnDefinition = "integer default 1")
    private Integer unidadMedida = 1;

    private BigDecimal precioUnitario;

    private Integer codigoProductoSin;

    @Column(length = 1024)
    private String imagen;

    private BigDecimal cantidad;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SucursalItemEntity> sucursalItems;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DespachoItemEntity> despachoItems;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PromocionEntity> promocionItems;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VentasDetalleEntity> ventasDetalles;

}