package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface SucursalItemCrudRepository extends CrudRepository<SucursalItemEntity, Integer> {
    List<SucursalItemEntity> findBySucursalId(Integer sucursalId);
    Optional<SucursalItemEntity> findBySucursalIdAndItemId(Integer sucursalId, Integer itemId);
}
