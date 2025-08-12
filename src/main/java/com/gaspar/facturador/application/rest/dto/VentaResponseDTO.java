package com.gaspar.facturador.application.rest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long idVenta;
    private String estado;
    private LocalDateTime fechaEmision;
    private BigDecimal montoTotal;
    private String metodoPago;
    private String nombreCliente;
    private List<DetalleVentaDTO> detalles;

    @Data
    public static class DetalleVentaDTO {
        private String descripcionProducto;
        private BigDecimal cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal descuento;
        private BigDecimal subtotal;
    }
}