package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoSignificativoDTO {
    private Long id;
    private Integer codigoMotivo;
    private String descripcionMotivo;
    private String cufdEvento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long codigoRecepcion;
    private boolean vigente;
}