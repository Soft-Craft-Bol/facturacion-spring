package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EgresosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    @Length(max = 1024)
    private String descripcion;

    @Column(name = "tipo_gasto")
    @Enumerated(EnumType.STRING)
    private GastoEnum gastoEnum;

    @NotNull
    @PositiveOrZero
    private double monto;

    @NotNull
    @Column(name="tipo_de_pago")
    @Enumerated(EnumType.STRING)
    private TipoPagoEnum tipoPagoEnum;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorEntity proovedor;

    @NotNull
    @Length(max = 1024)
    private String numFacturaComprobante;

    @NotNull
    private String observaciones;
}
