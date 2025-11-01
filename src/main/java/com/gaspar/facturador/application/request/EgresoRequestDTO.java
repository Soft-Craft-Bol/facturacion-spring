package com.gaspar.facturador.application.request;

import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EgresoRequestDTO {
    private LocalDate fechaDePago;
    private String descripcion;
    private GastoEnum gastoEnum;
    private double monto;
    private TipoPagoEnum tipoPagoEnum;
    private String pagadoA;
    private String numFacturaComprobante;
    private String observaciones;
    private Long cajaId;
}
