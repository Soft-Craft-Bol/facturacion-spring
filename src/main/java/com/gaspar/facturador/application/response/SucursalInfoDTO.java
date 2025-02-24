package com.gaspar.facturador.application.response;

import lombok.Data;

@Data
public class SucursalInfoDTO {
    private Integer id;
    private String nombre;
    private String departamento;
    private Integer cantidad;
}
