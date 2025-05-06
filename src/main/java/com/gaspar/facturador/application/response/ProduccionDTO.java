package com.gaspar.facturador.application.response;

import lombok.Data;

@Data
public class ProduccionDTO {
    private Integer recetaId;
    private Integer cantidad; // Cantidad de lotes a producir
    private Integer sucursalId;
    private String observaciones;


}