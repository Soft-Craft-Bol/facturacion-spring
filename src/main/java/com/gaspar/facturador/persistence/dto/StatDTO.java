package com.gaspar.facturador.persistence.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class StatDTO {
    private long numeroSucursales;
    private long inventario;
    private long facturasEmitidasHoy;
    private long numeroPuntosVenta;
    private long numeroUsuarios;
    private double totalVentasHoy;
    private long clientesRegistrados;
    private long totalPanaderos;
    // Constructor vacío
    public StatDTO() {}

}