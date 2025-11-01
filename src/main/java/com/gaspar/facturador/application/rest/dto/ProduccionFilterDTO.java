package com.gaspar.facturador.application.rest.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProduccionFilterDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer recetaId;
    private Integer productoId;
    private Integer sucursalId;
}