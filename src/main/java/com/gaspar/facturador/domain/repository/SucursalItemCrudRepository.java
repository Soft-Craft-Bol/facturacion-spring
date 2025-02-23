package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SucursalItemCrudRepository extends CrudRepository<SucursalItemEntity, Integer> {
}
