package com.gaspar.facturador.persistence.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "insumos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsumoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoInsumo tipo;

    private BigDecimal precioActual;

    private BigDecimal cantidad;

    private String unidades;

    private String imagen;
    private Boolean activo = true;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SucursalInsumoEntity> sucursalInsumo;

    @OneToMany(mappedBy = "insumo")
    private List<CompraInsumoEntity> compras;

}
