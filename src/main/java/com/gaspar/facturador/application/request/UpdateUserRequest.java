package com.gaspar.facturador.application.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Long telefono;
    private String photo;
    private String password;
    private Set<Long> puntosVentaIds;
    private List<String> roles;
}