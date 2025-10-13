package com.gaspar.facturador.application.request;


import lombok.Data;

@Data
public class AnularVentaRequest {
    private String motivo;
    private String usuario;
}