package com.gaspar.facturador.application.request;

import lombok.Data;

@Data
public class RecetaFiltroDTO {
    private String nombre;
    private Integer productoId;
    private Integer minUnidades;
    private Integer maxUnidades;
}