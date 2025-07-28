package com.gaspar.facturador.application.rest.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProduccionDTO {
    private Long id;
    private Long recetaId;
    private Integer productoId;
    private Integer sucursalId;
    private BigDecimal cantidad;
    private Integer cantidadProducida;
    private LocalDateTime fecha;
    private String observaciones;
}