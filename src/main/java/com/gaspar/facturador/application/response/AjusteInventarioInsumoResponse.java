package com.gaspar.facturador.application.response;

import java.math.BigDecimal;
import java.util.Date;

public record AjusteInventarioInsumoResponse(
        Long id,
        Date fechaAjuste,
        // Información de sucursal
        Integer sucursalId,
        String sucursalNombre,
        String sucursalDireccion,
        // Información completa del insumo
        Long insumoId,
        String insumoCodigo,
        String insumoNombre,
        String insumoDescripcion,
        String insumoUnidades,
        String tipoInsumo,
        // Datos del ajuste
        BigDecimal cantidadAnterior,
        BigDecimal cantidadNueva,
        BigDecimal diferencia,
        String tipoAjuste,
        String motivo,
        String usuarioResponsable,
        BigDecimal stockDespuesAjuste,
        // Información adicional
        String estadoAjuste
) {}