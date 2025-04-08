package com.gaspar.facturador.persistence.dto;

import lombok.Data;

import java.util.List;

@Data
public class CrearRecetaDTO {
    private String nombre;
    private Integer cantidadUnidades;
    private String peso;
    private Integer productoFinalId;
    private List<RecetaInsumoDTO> insumos;
}

