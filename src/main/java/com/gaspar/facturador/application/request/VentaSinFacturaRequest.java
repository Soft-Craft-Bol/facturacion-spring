package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VentaSinFacturaRequest {

    @NotBlank(message = "El usuario no puede estar vacío")
    private String usuario;

    @NotNull(message = "El ID del punto de venta es obligatorio")
    private Long idPuntoVenta;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    private String tipoComprobante;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer user_id;

    @NotNull(message = "El detalle no puede ser nulo")
    private List<VentaDetalleRequest> detalle;
}
