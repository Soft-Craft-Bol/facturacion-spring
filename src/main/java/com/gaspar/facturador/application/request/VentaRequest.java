package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


@ToString
@Getter
@Setter
public class VentaRequest {

    @NotBlank
    private String usuario;

    @NotNull
    private Integer idPuntoVenta;

    @NotNull
    private Integer idCliente;

    private Boolean nitInvalido;

    @NotNull
    private List<VentaDetalleRequest> detalle;

    private Integer cantidadFacturas;

    private Long codigoEvento;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    private String tipoComprobante;

    @NotNull(message = "Nombre de usuario obligatorio")
    private String username;

    private Integer codigoMetodoPago; // Nuevo campo para el método de pago
    private Long numeroTarjeta; // Opcional, para pagos con tarjeta

    private String cafc;
    private BigDecimal montoRecibido;
    private BigDecimal montoDevuelto;
    private Long cajasId;
}
