package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gaspar.facturador.persistence.entity.UserEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("SELECT u.id FROM UserEntity u WHERE u.username = :username")
    Long findIdByUsername(String username);

    @Query("SELECT DISTINCT u FROM UserEntity u JOIN u.roles r WHERE r.roleEnum IN :roles")
    Page<UserEntity> findByRolesIn(Set<RoleEnum> roles, Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.roleEnum = :roleEnum")
    List<UserEntity> findByRolesRoleEnum(RoleEnum roleEnum);

    long countByRoles(RoleEntity roleEntity);

}