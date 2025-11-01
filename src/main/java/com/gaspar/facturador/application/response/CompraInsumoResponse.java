package com.gaspar.facturador.application.response;

import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CompraInsumoResponse {
    private Long id;
    private Long insumoId;
    private String insumoNombre;
    private TipoInsumo tipoInsumo;
    private Long sucursalId;
    private String sucursalNombre;
    private Long proveedorId;
    private String proveedorNombre;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private LocalDate fecha;
    private String numeroFactura;
    private String notas;
    private BigDecimal total;
}

