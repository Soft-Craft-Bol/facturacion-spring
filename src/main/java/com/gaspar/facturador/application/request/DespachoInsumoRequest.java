package com.gaspar.facturador.application.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DespachoInsumoRequest {
    private Integer sucursalOrigen;
    private Integer sucursalDestino;
    private String responsable;
    private String observaciones;
    private List<DespachoInsumoItemRequest> items;

    @Data
    public static class DespachoInsumoItemRequest {
        private Long insumo;
        private BigDecimal cantidadEnviada;
    }
}