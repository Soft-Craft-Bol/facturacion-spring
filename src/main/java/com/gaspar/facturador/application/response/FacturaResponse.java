package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacturaResponse {

    private Integer codigoEstado;
    private String cuf;
    private Long numeroFactura;
}
