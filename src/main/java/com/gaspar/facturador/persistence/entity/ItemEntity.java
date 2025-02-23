package com.gaspar.facturador.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    private Integer unidadMedida;

    private BigDecimal precioUnitario;

    private Integer codigoProductoSin;

    @Column(length = 1024)
    private String imagen;

    private BigDecimal cantidad;//esta es la cantidad total

    @OneToMany(mappedBy = "item")
    private Set<SucursalItemEntity> sucursalItems;

    @PrePersist
    protected void onCreate() {
        this.codigo = UUID.randomUUID().toString();
    }

}
