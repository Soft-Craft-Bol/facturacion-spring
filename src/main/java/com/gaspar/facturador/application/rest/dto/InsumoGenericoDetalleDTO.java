package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

@Data
public class InsumoGenericoDetalleDTO {
    private Long id;
    private Long insumoId;
    private String nombreInsumo;
    private Integer prioridad;
}