package com.gaspar.facturador.application.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemWithSucursalesDTO {
    private Integer id;
    private String codigo;
    private String descripcion;
    private Integer unidadMedida;
    private BigDecimal precioUnitario;
    private BigDecimal precioConDescuento; // Nuevo campo
    private boolean tieneDescuento; // Nuevo campo
    private Integer codigoProductoSin;
    private String categoria;
    private String imagen;
    private List<SucursalInfoDTO> sucursales;
}
