package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface InsumoCrudRepository extends JpaRepository<InsumoEntity, Long> {
    List<InsumoEntity> findByActivoTrue();
    List<InsumoEntity> findByActivoFalse();
    boolean existsByNombre(String nombre);

    // Paginación
    Page<InsumoEntity> findAll(Pageable pageable);
    Page<InsumoEntity> findByActivoTrue(Pageable pageable);

    // Consulta para insumos por sucursal
    @Query("SELECT si FROM SucursalInsumoEntity si WHERE si.sucursal.id = :sucursalId")
    List<SucursalInsumoEntity> findInsumosBySucursalId(@Param("sucursalId") Long sucursalId);
}