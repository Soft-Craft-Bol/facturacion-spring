package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private SucursalEntity sucursalOrigen;
    @ManyToOne
    private SucursalEntity destino;
    private Date fechaEnvio;
    private String transporte;
    @Column(name = "numero_de_contacto")
    private long numeroContacto;
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = Date.valueOf(LocalDateTime.now().toLocalDate());
    }
}