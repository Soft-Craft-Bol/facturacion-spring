package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CajasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CajaRepository extends JpaRepository<CajasEntity, Long> {
    List<CajasEntity> findBySucursalId(Long sucursalId);
    boolean existsByNombreAndSucursalId(String nombre, Long sucursalId);
}
