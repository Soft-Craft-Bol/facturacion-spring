package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sucursal_item")
public class SucursalItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(nullable = false)
    private Integer cantidad;

    public void setSucursalId(Integer sucursalId) {
        if (sucursal == null) {
            sucursal = new SucursalEntity();
        }
        sucursal.setId(sucursalId);
    }
}
