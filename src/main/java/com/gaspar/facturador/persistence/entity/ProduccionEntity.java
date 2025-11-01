package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "producciones")
@Data
public class ProduccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Integer cantidadProducida; // Unidades del producto final

    @ManyToOne
    @JoinColumn(name = "receta_id")
    private RecetasEntity receta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ItemEntity producto;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProduccionInsumoEntity> insumosConsumidos = new ArrayList<>();

    private String observaciones;
}
