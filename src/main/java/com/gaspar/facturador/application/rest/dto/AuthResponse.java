package com.gaspar.facturador.application.rest.dto;

public record AuthResponse(
        String username,
        String message,
        String jwt,
        Boolean status,
        String photo) {
}
