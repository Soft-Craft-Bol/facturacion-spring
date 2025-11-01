package com.gaspar.facturador.application.rest.dto;
import com.gaspar.facturador.persistence.entity.enums.TipoComprobanteEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VentaListadoDTO {
    private Long id;
    private Date fecha;
    private TipoPagoEnum metodoPago;
    private BigDecimal monto;
    private String estado;
    private ClienteDTO cliente;
    private List<DetalleVentaDTO> detalles;
    private TipoComprobanteEnum tipoComprobante;
    private VendedorDTO vendedor;
    private PuntoVentaDTO puntoVenta;
    private FacturaResumenDTO factura;

    @Data
    public static class ClienteDTO {
        private Integer id;
        private String nombreRazonSocial;
        private String codigoCliente;
        private Long numeroDocumento;
    }

    @Data
    public static class DetalleVentaDTO {
        private Long id;
        private ProductoDTO producto;
        private BigDecimal cantidad;
        private BigDecimal precioUnitario;
        private String descripcionProducto;
    }

    @Data
    public static class ProductoDTO {
        private Integer id;
        private String codigo;
        private String descripcion;
        private Integer unidadMedida;
        private BigDecimal precioUnitario;
        private String imagen;
    }

    @Data
    public static class VendedorDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
    }

    @Data
    public static class PuntoVentaDTO {
        private Integer id;
        private String nombre;
        private SucursalDTO sucursal;
    }

    @Data
    public static class SucursalDTO {
        private Integer id;
        private String nombre;
        private String direccion;
    }

    @Data
    public static class FacturaResumenDTO {
        private Long id;
        private Integer numeroFactura;
        private String cuf;
        private String cufd;
        private String estado;
        private BigDecimal montoTotal;
    }
}