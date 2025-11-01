package com.gaspar.facturador.application.rest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InsumoGenericoDTO {
    private Long id;
    private String nombre;
    private String unidadMedida;
    private String descripcion;
    private List<InsumoGenericoDetalleDTO> insumosAsociados;
}