package com.gaspar.facturador.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    @Column(length = 500)
    private String descripcion;

    private Integer unidadMedida;

    private BigDecimal precioUnitario;

    private Integer codigoProductoSin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(Integer unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCodigoProductoSin() {
        return codigoProductoSin;
    }

    public void setCodigoProductoSin(Integer codigoProductoSin) {
        this.codigoProductoSin = codigoProductoSin;
    }
}
