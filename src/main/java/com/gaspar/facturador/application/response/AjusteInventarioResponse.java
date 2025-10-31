package com.gaspar.facturador.application.response;

import java.time.LocalDateTime;

public record AjusteInventarioResponse(
        Integer id,
        LocalDateTime fecha,
        Integer sucursalId,
        String sucursalNombre,
        Integer itemId,
        String itemCodigo,
        String itemDescripcion,
        Integer cantidadAjuste,
        String observacion,
        String usuario,
        Integer stockDespuesAjuste
) {}
