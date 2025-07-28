package com.gaspar.facturador.persistence.dto;

import lombok.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {
    private Long id;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private List<String> dias;
}