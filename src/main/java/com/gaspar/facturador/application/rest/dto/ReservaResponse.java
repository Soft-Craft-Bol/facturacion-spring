package com.gaspar.facturador.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponse {

    private Integer id;
    private LocalDateTime fechaReserva;
    private String estado;
    private String metodoPago;
    private String comprobante;
    private String mensaje;
    private List<ItemReservaResponse> items; // Lista de items reservados
}