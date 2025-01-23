package com.gaspar.facturador.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class DatosCUF {

    private long nitEmisor;
    private LocalDateTime fechaHora;
    private int sucursal;
    private int modalidad;
    private int tipoEmision;
    private int tipoFacturaDocumentoAjuste;
    private int tipoDocumentoSector;
    private long numeroFactura;
    private int puntoVenta;

    public DatosCUF() {
    }
}
