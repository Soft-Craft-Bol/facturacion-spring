package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private RoleEnum roleEnum;
    private Set<String> permissions;
}
