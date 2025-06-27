package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "despacho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DespachoItemEntity> despachoItems;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = Date.valueOf(LocalDateTime.now().toLocalDate());
    }


}