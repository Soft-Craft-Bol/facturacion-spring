package com.gaspar.facturador.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CajaAbiertaDTO {
    private Long id;
    private String nombre;
    private String estado;
    private String turno;
    private BigDecimal montoInicial;
    private LocalDateTime fechaApertura;
}