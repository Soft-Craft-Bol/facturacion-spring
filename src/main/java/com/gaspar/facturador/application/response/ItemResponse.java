package com.gaspar.facturador.application.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemResponse {
    private Integer id;
    private String codigo;
    private String descripcion;
    private Integer unidadMedida;
    private BigDecimal precioUnitario;
    private Integer codigoProductoSin;
    private String imagen;
    private CategoriaSimpleResponse categoria;

    @Data
    public static class CategoriaSimpleResponse {
        private Integer id;
        private String nombre;
    }
}