package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.rest.dto.CreateRoleRequest;
import com.gaspar.facturador.application.rest.dto.PermissionDTO;
import com.gaspar.facturador.application.rest.dto.RoleDTO;
import com.gaspar.facturador.application.rest.dto.UpdateRoleRequest;
import com.gaspar.facturador.domain.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RolePermissionService rolePermissionService;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody CreateRoleRequest request) {
        RoleDTO newRole = rolePermissionService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<RoleDTO> updateRolePermissions(
            @PathVariable Long roleId,
            @RequestBody UpdateRoleRequest request) {
        RoleDTO updatedRole = rolePermissionService.updateRolePermissions(roleId, request);
        return ResponseEntity.ok(updatedRole);
    }

    @PatchMapping("/{roleId}/status")
    public ResponseEntity<Void> toggleRoleStatus(
            @PathVariable Long roleId,
            @RequestParam boolean active) {
        rolePermissionService.toggleRoleStatus(roleId, active);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(rolePermissionService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(rolePermissionService.getRoleById(id));
    }

    // Endpoints para permisos
    @PostMapping("/permissions")
    public ResponseEntity<PermissionDTO> createPermission(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolePermissionService.createPermission(name));
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(rolePermissionService.getAllPermissions());
    }
}