package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaquetesResponse {
    private Integer codigoEstado;
    private String codigoRecepcion;
    private String mensaje;
    private Long codigoEvento;
    private Integer cantidadFacturasAlmacenadas;
}