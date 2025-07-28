package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.MovimientoProduccionEntity;
import com.gaspar.facturador.persistence.entity.ProduccionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ProduccionCrudRepository  extends JpaRepository<ProduccionEntity, Long> {
    @Query("SELECT m FROM MovimientoProduccionEntity m WHERE " +
            "(COALESCE(:fechaInicio, null) IS NULL OR m.fechaProduccion >= :fechaInicio) AND " +
            "(COALESCE(:fechaFin, null) IS NULL OR m.fechaProduccion <= :fechaFin) AND " +
            "(:recetaId IS NULL OR m.receta.id = :recetaId) AND " +
            "(:productoId IS NULL OR m.receta.producto.id = :productoId) AND " +
            "(:sucursalId IS NULL OR m.sucursalId = :sucursalId)")
    Page<MovimientoProduccionEntity> findWithFilters(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("recetaId") Long recetaId,
            @Param("productoId") Integer productoId,
            @Param("sucursalId") Long sucursalId,
            Pageable pageable);
}
