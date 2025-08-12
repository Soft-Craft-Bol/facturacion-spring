package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.rest.dto.CreateRoleRequest;
import com.gaspar.facturador.application.rest.dto.PermissionDTO;
import com.gaspar.facturador.application.rest.dto.RoleDTO;
import com.gaspar.facturador.application.rest.dto.UpdateRoleRequest;
import com.gaspar.facturador.persistence.crud.PermissionRepository;
import com.gaspar.facturador.persistence.crud.RoleRepository;
import com.gaspar.facturador.persistence.entity.PermissionEntity;
import com.gaspar.facturador.persistence.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // Crear nuevo rol con permisos
    public RoleDTO createRole(CreateRoleRequest request) {
        // Verificar si el rol ya existe
        if (roleRepository.findByRoleEnum(request.roleEnum()).isPresent()) {
            throw new IllegalArgumentException("El rol ya existe");
        }

        // Obtener o crear permisos
        Set<PermissionEntity> permissions = request.permissionNames().stream()
                .map(permissionName -> permissionRepository.findByName(permissionName)
                        .orElseGet(() -> {
                            PermissionEntity newPermission = new PermissionEntity();
                            newPermission.setName(permissionName);
                            return permissionRepository.save(newPermission);
                        }))
                .collect(Collectors.toSet());

        // Crear y guardar el rol
        RoleEntity newRole = RoleEntity.builder()
                .roleEnum(request.roleEnum())
                .permissions(permissions)
                .build();

        RoleEntity savedRole = roleRepository.save(newRole);
        return mapToDTO(savedRole);
    }

    // Actualizar permisos de un rol
    public RoleDTO updateRolePermissions(Long roleId, UpdateRoleRequest request) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        Set<PermissionEntity> permissions = request.permissionNames().stream()
                .map(permissionName -> permissionRepository.findByName(permissionName)
                        .orElseThrow(() -> new IllegalArgumentException("Permiso no válido: " + permissionName)))
                .collect(Collectors.toSet());

        role.setPermissions(permissions);
        RoleEntity updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    // Activar/desactivar rol (soft delete)
    public void toggleRoleStatus(Long roleId, boolean active) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        // Implementa lógica de activación/desactivación según tu modelo
        // role.setActive(active);
        roleRepository.save(role);
    }

    public List<RoleDTO> getAllRoles() {
        List<RoleEntity> roles = (List<RoleEntity>) roleRepository.findAll();
        List<RoleDTO> roleDTOs = new ArrayList<>();

        for (RoleEntity role : roles) {
            roleDTOs.add(mapToDTO(role));
        }

        return roleDTOs;
    }

    // Obtener rol por ID
    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
    }

    // Mapear entidad a DTO
    private RoleDTO mapToDTO(RoleEntity role) {
        Set<String> permissionNames = role.getPermissions().stream()
                .map(PermissionEntity::getName)
                .collect(Collectors.toSet());

        return new RoleDTO(role.getId(), role.getRoleEnum(), permissionNames);
    }

    // Métodos para permisos
    public PermissionDTO createPermission(String permissionName) {
        if (permissionRepository.findByName(permissionName).isPresent()) {
            throw new IllegalArgumentException("El permiso ya existe");
        }

        PermissionEntity permission = new PermissionEntity();
        permission.setName(permissionName);
        PermissionEntity savedPermission = permissionRepository.save(permission);
        return new PermissionDTO(savedPermission.getId(), savedPermission.getName());
    }

    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(p -> new PermissionDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}