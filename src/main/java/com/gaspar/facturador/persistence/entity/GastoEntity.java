package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "gastos")
public class GastoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GastoEnum categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private ProveedorEntity proveedor;

    private String numeroFactura;
    private String notas;

    @ManyToOne
    private SucursalEntity sucursal;

    @OneToOne(mappedBy = "gasto")
    private CompraInsumoEntity compraInsumo;
}