package com.gaspar.facturador.application.response;

import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class InsumoSucursalResponse {
    private Long insumoId;
    private String nombre;
    private TipoInsumo tipo;
    private String unidades;
    private BigDecimal cantidad;
    private BigDecimal stockMinimo;
    private Date fechaVencimiento;
    private Boolean activo;
}