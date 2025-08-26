package com.gaspar.facturador.application.response;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DespachoInsumoResponse {
    private Long id;
    private SucursalMinResponse sucursalOrigen;
    private SucursalMinResponse sucursalDestino;
    private LocalDateTime fechaDespacho;
    private String responsable;
    private String observaciones;
    private String estado;
    private List<DespachoInsumoItemResponse> items;


    @Data
    @Builder
    public static class SucursalMinResponse {
        private Integer id;
        private String nombre;
        private String direccion;
    }

    @Data
    @Builder
    public static class DespachoInsumoItemResponse {
        private Long id;
        private InsumoMinResponse insumo;
        private BigDecimal cantidadEnviada;
        private BigDecimal cantidadRecibida;
    }

    @Data
    @Builder
    public static class InsumoMinResponse {
        private Long id;
        private String nombre;
        private String unidades;
    }

}
