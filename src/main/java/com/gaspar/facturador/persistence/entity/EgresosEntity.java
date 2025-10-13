package com.gaspar.facturador.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "egresos")
public class EgresosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDePago;

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

//    @ManyToOne
//    @JoinColumn(name = "id_proveedor", nullable = false)
//    private ProveedorEntity proovedor;
    @Length(max = 1024)
    private String pagadoA;

    @Length(max = 1024)
    private String numFacturaComprobante;

    @NotNull
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "caja_id")
    private CajasEntity caja;
}
