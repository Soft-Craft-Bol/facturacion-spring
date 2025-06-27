package com.gaspar.facturador.persistence.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "insumos")
public class InsumoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoInsumo tipo;

    private BigDecimal precioActual;

    private String unidades;

    private String imagen;
    private Boolean activo = true;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SucursalInsumoEntity> sucursalInsumo;

    @OneToMany(mappedBy = "insumo")
    private List<CompraInsumoEntity> compras;

}
