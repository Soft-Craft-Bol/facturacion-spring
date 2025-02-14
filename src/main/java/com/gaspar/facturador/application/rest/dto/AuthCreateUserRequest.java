package com.gaspar.facturador.application.rest.dto;


import jakarta.validation.constraints.NotBlank;


public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String password,
                                    @NotBlank String email,
                                    @NotBlank String telefono,
                                    @NotBlank String nombre,
                                    @NotBlank String apellido,
                                    String photo,
                                    AuthCreateRoleRequest roleRequest) {
}
