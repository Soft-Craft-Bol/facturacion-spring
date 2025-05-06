package com.gaspar.facturador.application.response;

import com.gaspar.facturador.persistence.entity.PromocionEntity;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SucursalInfoDTO {
    private Integer sucursalId;
    private String nombre;
    private String departamento;
    private Integer cantidad;
    private boolean tieneDescuento;
    private BigDecimal precioConDescuento;

    public SucursalInfoDTO(Integer id, @NotEmpty String nombre, @NotEmpty String departamento, Integer cantidad) {
        this.sucursalId = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.cantidad = cantidad;
    }

    public void setId(Integer id) {
        this.sucursalId = id;
    }
}
