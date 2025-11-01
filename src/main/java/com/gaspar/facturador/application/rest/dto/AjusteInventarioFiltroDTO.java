
package com.gaspar.facturador.application.rest.dto;

import java.time.LocalDate;

public record AjusteInventarioFiltroDTO(
        Integer sucursalId,
        Integer itemId,
        String itemCodigo,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        String usuario
) {}