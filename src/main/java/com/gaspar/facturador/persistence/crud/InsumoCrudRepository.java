package com.gaspar.facturador.persistence.crud;

import aj.org.objectweb.asm.commons.Remapper;
import com.gaspar.facturador.persistence.entity.InsumoEntity;
import com.gaspar.facturador.persistence.entity.SucursalInsumoEntity;
import com.gaspar.facturador.persistence.entity.enums.TipoInsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface InsumoCrudRepository extends JpaRepository<InsumoEntity, Long> {

    // Consultas básicas
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);

    // Consultas con estado activo
    List<InsumoEntity> findByActivoTrue();
    Optional<InsumoEntity> findByIdAndActivoTrue(Long id);

    // Consultas paginadas
    Page<InsumoEntity> findAllByActivo(boolean activo, Pageable pageable);

    @Query("SELECT i FROM InsumoEntity i WHERE LOWER(i.nombre) LIKE LOWER(concat('%', :nombre,'%')) AND i.activo = :activo")
    Page<InsumoEntity> findByNombreContainingIgnoreCaseAndActivo(
            @Param("nombre") String nombre,
            @Param("activo") boolean activo,
            Pageable pageable);

    Page<InsumoEntity> findByTipoAndActivo(TipoInsumo tipo, boolean activo, Pageable pageable);

    @Query("SELECT i FROM InsumoEntity i WHERE LOWER(i.nombre) LIKE LOWER(concat('%', :nombre,'%')) " +
            "AND i.tipo = :tipo AND i.activo = :activo")
    Page<InsumoEntity> findByNombreContainingIgnoreCaseAndTipoAndActivo(
            @Param("nombre") String nombre,
            @Param("tipo") TipoInsumo tipo,
            @Param("activo") boolean activo,
            Pageable pageable);

    // Consultas para sucursales
    @Query("SELECT si FROM SucursalInsumoEntity si WHERE si.sucursal.id = :sucursalId AND si.insumo.activo = true")
    List<SucursalInsumoEntity> findActiveInsumosBySucursalId(@Param("sucursalId") Long sucursalId);

    // Consultas existentes
    Page<InsumoEntity> findAll(Pageable pageable);

    @Query("SELECT si FROM SucursalInsumoEntity si WHERE si.sucursal.id = :sucursalId")
    List<SucursalInsumoEntity> findInsumosBySucursalId(@Param("sucursalId") Long sucursalId);

    Page<InsumoEntity> findByNombreContainingIgnoreCaseAndTipo(
            String nombre, TipoInsumo tipo, Pageable pageable);

    Page<InsumoEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Page<InsumoEntity> findByTipo(TipoInsumo tipo, Pageable pageable);
}