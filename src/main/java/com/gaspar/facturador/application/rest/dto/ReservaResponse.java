package com.gaspar.facturador.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal anticipo;
    private BigDecimal saldoPendiente;
    private String observaciones;
    private Integer idCliente;
    private String nombreCliente;
    private Integer idPuntoVenta;
    private String nombrePuntoVenta;
    private boolean stockSuficiente; // Nuevo campo
    private BigDecimal stockDisponible;
    private List<ItemReservaResponse> items;


}