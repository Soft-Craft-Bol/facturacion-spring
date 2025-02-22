package com.gaspar.facturador.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponse {

    private Integer id;
    private LocalDateTime fechaReserva;
    private String estado;
    private String mensaje;
}