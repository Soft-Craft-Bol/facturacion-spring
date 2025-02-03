package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RoleEntity;
import com.gaspar.facturador.persistence.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<RoleEnum> roleNames);
}

