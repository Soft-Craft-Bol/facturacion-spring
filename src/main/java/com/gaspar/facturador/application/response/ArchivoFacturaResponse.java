package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArchivoFacturaResponse {
    private String nombre;
    private String ruta;
    private boolean esDirectorio;
    private long tamanio;
    private LocalDateTime fechaModificacion;
    private String tipoEmision; // "normal" o "contingencia"
    private String sucursal;
    private String cufd;
    private List<ArchivoFacturaResponse> archivosHijos;
}