package com.gaspar.facturador.persistence.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
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
    private String proveedor;
    private String marca;
    private BigDecimal precio;
    private String unidades;
    private String descripcion;
    //private Date ultimaAdquisicion;
    private String imagen;
    private Boolean activo = true;
    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SucursalInsumoEntity> sucursalInsumo;


}
