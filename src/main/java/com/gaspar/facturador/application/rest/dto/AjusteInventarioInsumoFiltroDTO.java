package com.gaspar.facturador.application.rest.dto;

import java.time.LocalDate;

public record AjusteInventarioInsumoFiltroDTO(
        Integer sucursalId,
        Long insumoId,
        String insumoCodigo,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        String usuarioResponsable,
        String tipoAjuste
) {}