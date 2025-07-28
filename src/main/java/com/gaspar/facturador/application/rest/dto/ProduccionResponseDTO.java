package com.gaspar.facturador.application.rest.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProduccionResponseDTO {
    private Long id;
    private String recetaNombre;
    private String productoNombre;
    private String sucursalNombre;
    private Integer cantidadProducida;
    private LocalDateTime fecha;
    private String observaciones;
}