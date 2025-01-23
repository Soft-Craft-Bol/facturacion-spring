package com.gaspar.facturador.application.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
}
