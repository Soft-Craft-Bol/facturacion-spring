package com.gaspar.facturador.application.rest.dto;

import java.math.BigDecimal;

public record ReporteProductoDTO(
        Integer idProducto,
        String descripcionProducto,
        BigDecimal totalCantidad,
        BigDecimal totalVenta      // Cambiar de BigDecimal a Long
) {}