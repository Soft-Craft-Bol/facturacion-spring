package com.gaspar.facturador.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoMermaResponse {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal cantidad;
    private String tipo;
    private String motivo;
    private Long sucursalId;
    private String sucursalNombre;
    private Long insumoId;
    private String insumoNombre;
    private Integer itemId;
    private String itemNombre;
    private String registradoPor;
}