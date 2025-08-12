package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "despacho_insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespachoInsumoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_origen_id", nullable = false)
    private SucursalEntity sucursalOrigen;

    @ManyToOne
    @JoinColumn(name = "sucursal_destino_id", nullable = false)
    private SucursalEntity sucursalDestino;

    private LocalDateTime fechaDespacho;
    private String responsable;
    private String observaciones;
    private String estado; // "PENDIENTE", "EN_TRANSITO", "RECIBIDO", "CANCELADO"

    @OneToMany(mappedBy = "despacho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DespachoInsumoItemEntity> items;

    @PrePersist
    protected void onCreate() {
        this.fechaDespacho = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }
}