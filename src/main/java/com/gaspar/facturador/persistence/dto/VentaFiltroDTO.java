package com.gaspar.facturador.persistence.dto;

import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaFiltroDTO {
    private Long idVenta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long idPuntoVenta;
    private String nombrePuntoVenta;
    private String nombreSucursal;
    private Long idUsuario;
    private String nombreUsuario;
    private String codigoCliente;
    private String nombreCliente;
    private TipoComprobanteEnum tipoComprobante;
    private TipoPagoEnum metodoPago;
    private String estado;
    private BigDecimal montoMinimo;
    private BigDecimal montoMaximo;
    private String productoNombre;
    private List<VentaDetalleDTO> detalles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VentaDetalleDTO {
        private Long idProducto;
        private String descripcionProducto;
        private BigDecimal cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal montoDescuento;
        private BigDecimal subTotal;
    }
}