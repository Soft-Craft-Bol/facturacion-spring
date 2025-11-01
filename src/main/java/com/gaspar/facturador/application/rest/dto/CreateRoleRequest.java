package com.gaspar.facturador.application.rest.dto;

import com.gaspar.facturador.persistence.entity.RoleEnum;

import java.util.Set;

public record CreateRoleRequest(
        RoleEnum roleEnum,
        Set<String> permissionNames
) {}