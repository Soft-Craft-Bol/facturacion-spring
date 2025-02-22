package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "despacho")
public class DespachoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_origen", nullable = false)
    private SucursalEntity sucursalOrigen;

    @ManyToOne
    @JoinColumn(name = "sucursal_destino", nullable = false)
    private SucursalEntity sucursalDestino;

    private Date fechaEnvio;
    private String transporte;

    @Column(name = "numero_de_contacto")
    private long numeroContacto;

    private String observaciones;

    @ManyToMany
    @JoinTable(
            name = "despacho_items",
            joinColumns = @JoinColumn(name = "despacho_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<ItemEntity> items;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = Date.valueOf(LocalDateTime.now().toLocalDate());
    }
}