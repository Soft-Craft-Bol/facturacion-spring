package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalItemEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.*;

public interface SucursalItemCrudRepository extends JpaRepository<SucursalItemEntity, Integer>, JpaSpecificationExecutor<SucursalItemEntity> {
    List<SucursalItemEntity> findByItemId(Integer itemId);
    List<SucursalItemEntity> findBySucursal_Id(Integer sucursalId);
    Optional<SucursalItemEntity> findBySucursal_IdAndItem_Id(Integer sucursalId, Integer itemId);

    @EntityGraph(attributePaths = {"item", "item.promocionItems"})
    @Override
    Page<SucursalItemEntity> findAll(Specification<SucursalItemEntity> spec, Pageable pageable);

    Optional<SucursalItemEntity> findBySucursalAndItem(@NotNull SucursalEntity sucursal, ItemEntity item);
}
