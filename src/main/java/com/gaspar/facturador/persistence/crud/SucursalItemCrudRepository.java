package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface SucursalItemCrudRepository extends CrudRepository<SucursalItemEntity, Integer> {
    List<SucursalItemEntity> findByItemId(Integer itemId);
    List<SucursalItemEntity> findBySucursal_Id(Integer sucursalId);
    Optional<SucursalItemEntity> findBySucursal_IdAndItem_Id(Integer sucursalId, Integer itemId);

}
