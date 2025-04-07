package com.gaspar.facturador.application.rest.dto;

public record EnvioPaqueteRequest(
        Integer idPuntoVenta,
        Long codigoEvento,
        String cafc
) {
}