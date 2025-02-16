package com.gaspar.facturador.application.response;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PaquetesResponse {
    private Integer codigoEstado;
    private String cuf;
    private Long numeroFactura;
    private String mensaje;
    private String codigoRecepcion; // Nuevo campo para el código de recepción
    private Long codigoEvento; // Nuevo campo para el código de evento
}