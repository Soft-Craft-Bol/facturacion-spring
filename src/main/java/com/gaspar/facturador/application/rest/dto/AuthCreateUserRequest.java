package com.gaspar.facturador.application.rest.dto;

import javax.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String username,
                                     @NotBlank String password,
                                     AuthCreateRoleRequest roleRequest) {
}
