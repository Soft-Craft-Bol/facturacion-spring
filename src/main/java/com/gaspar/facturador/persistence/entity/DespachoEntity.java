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
    @Column(name = "sucursal_origen_id")
    private Long sucursalOrigenId;
    @Column(name = "destino_id")
    private Long destinoId;
    private Date fechaEnvio;
    private String transporte;
    @Column(name = "numero_de_contacto")
    private long numeroContacto;
    private String observaciones;
    @ElementCollection
    @CollectionTable(name = "despacho_items", joinColumns = @JoinColumn(name = "despacho_id"))
    @Column(name = "item_id")
    private List<Long> itemIds;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = Date.valueOf(LocalDateTime.now().toLocalDate());
    }
}