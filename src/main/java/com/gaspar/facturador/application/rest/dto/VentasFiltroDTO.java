package com.gaspar.facturador.application.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class VentasFiltroDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;

    private String busqueda; // Puede ser número de documento o nombre/razón social
    private String estado;
    private Integer idPuntoVenta;
    private String tipoBusqueda = "cliente";

    // Paginación
    private int pagina = 0;
    private int tamanio = 10;
}