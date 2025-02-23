package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface SucursalItemCrudRepository extends CrudRepository<SucursalItemEntity, Integer> {
}
