package com.gaspar.facturador.persistence.dto;

import lombok.Data;

@Data
public class ProduccionDTO {
    private Integer recetaId;
    private Integer cantidadDeseada; // Ej: producir 144 unidades
}
