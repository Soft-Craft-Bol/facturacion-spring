package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CufdEventoDTO {
    private Integer idPuntoVenta;
    private String cufdEvento;
    private LocalDateTime fechaHoraInicioEvento;
    private LocalDateTime fechaHoraFinEvento;
}
