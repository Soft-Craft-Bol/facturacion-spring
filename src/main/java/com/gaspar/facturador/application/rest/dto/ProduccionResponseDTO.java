package com.gaspar.facturador.application.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProduccionResponseDTO {
    private Long id;
    private String recetaNombre;
    private String productoNombre;
    private String sucursalNombre;
    private Integer cantidadProducida;
    private LocalDateTime fecha;
    private String observaciones;
    private List<DetalleInsumoResponseDTO> insumosUsados; // Opcional

    @Data
    public static class DetalleInsumoResponseDTO {
        private String insumoGenericoNombre;
        private String insumoNombre;
        private BigDecimal cantidadUsada;
        private String unidadMedida;
        private BigDecimal porcentajeUsado;
    }
}

