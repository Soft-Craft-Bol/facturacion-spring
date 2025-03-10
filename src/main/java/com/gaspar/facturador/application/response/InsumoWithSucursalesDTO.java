package com.gaspar.facturador.application.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InsumoWithSucursalesDTO {
    private Long id;
    private String nombre;
    private String proveedor;
    private String marca;
    private BigDecimal precio;
    private String unidades;
    private String descripcion;
    private String imagen;
    private List<SucursalInfoDTO> sucursales;
}
