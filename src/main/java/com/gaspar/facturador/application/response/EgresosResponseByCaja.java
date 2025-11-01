package com.gaspar.facturador.application.response;

import com.gaspar.facturador.persistence.entity.enums.GastoEnum;
import com.gaspar.facturador.persistence.entity.enums.TipoPagoEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EgresosResponseByCaja {
    private Long id;
    private LocalDate fechaDePago;
    private String descripcion;
    private GastoEnum gastoEnum;
    private double monto;
    private TipoPagoEnum tipoPagoEnum;
    private String pagadoA;
    private String numFacturaComprobante;
    private String observaciones;

    private Long cajaId;
    private String cajaNombre;
}
