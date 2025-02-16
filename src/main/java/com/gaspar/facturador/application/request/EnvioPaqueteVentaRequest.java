package com.gaspar.facturador.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnvioPaqueteVentaRequest {
    @NotBlank
    private String usuario;

    @NotNull
    private Integer idPuntoVenta;

    @NotNull
    private Integer idCliente;

    private Boolean nitInvalido;

    @NotNull
    private List<VentaDetalleRequest> detalle;
}