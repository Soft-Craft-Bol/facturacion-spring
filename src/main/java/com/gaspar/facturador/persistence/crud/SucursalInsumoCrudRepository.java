package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SucursalInsumoCrudRepository extends CrudRepository<SucursalInsumoEntity,Long> {
    List<SucursalInsumoEntity> findByInsumoId(Long insumoId);
    List<SucursalInsumoEntity> findBySucursalId(Integer sucursalId);
    Optional<SucursalInsumoEntity> findBySucursalIdAndInsumoId(Integer sucursalId, Long insumoId);
}
