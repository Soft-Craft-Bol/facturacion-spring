package com.gaspar.facturador.application.rest.dto;

import javax.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
