package com.gaspar.facturador.application.rest.dto;

public record AjusteInventarioDTO(
        Integer sucursalId,
        Integer itemId,
        Integer cantidadAjuste,
        String observacion,
        String usuario
) { }
