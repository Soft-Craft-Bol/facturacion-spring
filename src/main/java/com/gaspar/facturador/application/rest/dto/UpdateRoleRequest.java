package com.gaspar.facturador.application.rest.dto;

import java.util.Set;

public record UpdateRoleRequest(
        Set<String> permissionNames
) {}