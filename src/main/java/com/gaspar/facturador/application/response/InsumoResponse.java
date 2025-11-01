package com.gaspar.facturador.application.response;

import com.fasterxml.jackson.core.JsonToken;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class InsumoResponse {
    private Long id;
    private String nombre;
    private TipoInsumo tipo;
    private BigDecimal precioActual;
    private BigDecimal cantidad;
    private String unidades;
    private String imagen;
    private Boolean activo;


}