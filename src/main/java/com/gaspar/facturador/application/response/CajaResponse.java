package com.gaspar.facturador.application.response;

import lombok.Data;

@Data
public class CajaResponse {
    private Long id;
    private String nombre;
    private String estado;
    private Long sucursalId;
    private String nombreSucursal;
}