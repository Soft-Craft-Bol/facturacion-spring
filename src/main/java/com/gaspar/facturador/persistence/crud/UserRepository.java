package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import com.gaspar.facturador.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
    //get id of user by username
    @Query("SELECT u.id FROM UserEntity u WHERE u.username = :username")
    Long findIdByUsername(String username);

    List<UserEntity> findAll();
    void deleteById(long id);
    long countByRoles(RoleEntity role);
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.roleEnum = :roleEnum")
    List<UserEntity> findByRolesRoleEnum(RoleEnum roleEnum);
}