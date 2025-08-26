package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SucursalInsumoCrudRepository extends JpaRepository<SucursalInsumoEntity, Long> {
    List<SucursalInsumoEntity> findByInsumoId(Long insumoId);
    List<SucursalInsumoEntity> findBySucursalId(Long sucursalId);
    Optional<SucursalInsumoEntity> findBySucursalIdAndInsumoId(Integer sucursalId, Long insumoId);
    boolean existsBySucursalIdAndInsumoId(Long sucursalId, Long insumoId);

    Optional<SucursalInsumoEntity> findBySucursalAndInsumo(SucursalEntity sucursal, InsumoEntity insumo);
}
