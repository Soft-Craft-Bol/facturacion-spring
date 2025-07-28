package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoSucursalDto {
    private Integer id;
    private String codigo;
    private String descripcion;
    private Integer unidadMedida;
    private BigDecimal precioUnitario;
    private BigDecimal precioConDescuento;
    private boolean tieneDescuento;
    private Integer codigoProductoSin;
    private String imagen;
    private Integer cantidadDisponible; // Quantity available in this specific sucursal

    // Sucursal information (simplified)
    private Integer sucursalId;
    private String sucursalNombre;
    private String sucursalDireccion;
}