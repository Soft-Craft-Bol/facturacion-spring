package com.gaspar.facturador.persistence.dto;

import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaHoyDTO {

    private Long idVenta;
    private String codigoCliente;
    private String nombreRazonSocial;
    private String cuf;
    private LocalDateTime fechaEmision;
    private String estado;
    private TipoComprobanteEnum tipoComprobante;
    private List<VentaDetalleDTO> detalles;
    private String nombrePuntoVenta;
    private Integer idPuntoVenta;
    private String nombreSucursal;
    private Long idUsuario;
    private String nombreUsuario;

    @Data
    public static class VentaDetalleDTO {
        private String descripcion;
        private BigDecimal cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal montoDescuento;
        private BigDecimal subTotal;
    }
}