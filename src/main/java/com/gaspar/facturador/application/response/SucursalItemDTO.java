package com.gaspar.facturador.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SucursalItemDTO {
    private Integer id;
    private Integer itemId;
    private Integer sucursalId;
    private Integer cantidad;
    private String nombreSucursal;
    private String departamento;
}