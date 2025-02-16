package com.gaspar.facturador.application.rest.dto;

public record EnvioPaqueteRequest(
        Long idPuntoVenta,
        int cantidadFacturas,
        Long codigoEvento
) {
}