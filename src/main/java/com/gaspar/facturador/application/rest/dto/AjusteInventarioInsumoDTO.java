package com.gaspar.facturador.application.rest.dto;

import java.math.BigDecimal;

public record AjusteInventarioInsumoDTO(
        Integer sucursalId,
        Long insumoId,
        BigDecimal cantidadAjuste,
        String motivo,
        String usuarioResponsable
) { }
