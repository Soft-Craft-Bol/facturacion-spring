package com.gaspar.facturador.application.response;


import com.gaspar.facturador.persistence.entity.enums.TipoProveedor;
import lombok.Data;

@Data
public class ProveedorResponse {
    private Long id;
    private String nombreRazonSocial;
    private TipoProveedor tipoProveedor;
    private String direccion;
    private Long telefono;
    private String email;
}