package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.TipoMerma;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movimientos_merma")
public class MovimientoMermaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private BigDecimal cantidad;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoMerma tipo; // MERMA, DONACION, OTRO

    @Column(length = 500)
    private String motivo;

    @ManyToOne
    private SucursalEntity sucursal;

    @ManyToOne
    private InsumoEntity insumo;

    @ManyToOne
    private ItemEntity item;

    private String registradoPor; // nombre de usuario, opcional

}
