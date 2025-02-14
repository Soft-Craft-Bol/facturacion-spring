package com.gaspar.facturador.persistence.entity;

import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


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
