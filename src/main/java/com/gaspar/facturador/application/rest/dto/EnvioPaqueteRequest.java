package com.gaspar.facturador.application.rest.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record EnvioPaqueteRequest(
        @NotNull Integer idPuntoVenta,
        @NotNull Long codigoEvento,
        @Nullable String cafc
) {
}
