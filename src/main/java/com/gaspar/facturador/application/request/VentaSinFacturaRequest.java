package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaSinFacturaRequest {

    private Integer idCliente;

    @NotNull(message = "El ID del punto de venta es obligatorio")
    private Long idPuntoVenta;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    private String tipoComprobante;

    @NotNull(message = "Nombre de usuario obligatorio")
    private String username;

    @NotNull(message = "El detalle no puede ser nulo")
    private List<VentaDetalleRequest> detalle;

    @NotNull(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Size(max = 3, message = "Máximo 3 métodos de pago permitidos")
    private List<MetodoPagoRequest> metodosPago;

    private BigDecimal montoRecibido;
    private BigDecimal montoDevuelto;

    private Long idfactura;
    private Long cajaId;

    private Boolean esCredito = false;
    private Integer diasCredito;
}
