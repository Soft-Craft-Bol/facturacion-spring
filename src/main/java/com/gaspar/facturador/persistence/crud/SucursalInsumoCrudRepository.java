package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalInsumoCrudRepository extends JpaRepository<SucursalInsumoEntity, Long>,
        JpaSpecificationExecutor<SucursalInsumoEntity> {
    Optional<SucursalInsumoEntity> findBySucursalIdAndInsumoId(Integer sucursalId, Long insumoId);
    boolean existsBySucursalIdAndInsumoId(Long sucursalId, Long insumoId);
    Optional<SucursalInsumoEntity> findBySucursalAndInsumo(SucursalEntity sucursal, InsumoEntity insumo);
    Optional<SucursalInsumoEntity> findBySucursal_IdAndInsumo_Id(Integer sucursalId, Long insumoId);
}
