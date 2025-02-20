package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.persistence.crud.RoleRepository;
import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<RoleEntity> getRoleEntity(RoleEnum roleEnum) {
        return roleRepository.findByRoleEnum(roleEnum);
    }
}
