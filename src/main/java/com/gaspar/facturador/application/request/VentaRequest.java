package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer user_id;

    @NotNull(message = "El método de pago es obligatorio")
    private String metodoPago;

}
