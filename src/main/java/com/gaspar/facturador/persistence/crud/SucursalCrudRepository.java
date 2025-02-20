package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.SucursalEntity;
import org.springframework.data.repository.CrudRepository;


public interface SucursalCrudRepository extends CrudRepository<SucursalEntity, Integer> {
    long count();
}
