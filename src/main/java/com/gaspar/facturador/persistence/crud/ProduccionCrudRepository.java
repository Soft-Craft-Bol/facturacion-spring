package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ProduccionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ProduccionCrudRepository  extends JpaRepository<ProduccionEntity, Long> {

        @Query("SELECT p FROM ProduccionEntity p WHERE " +
                "(cast(:fechaInicio as timestamp) IS NULL OR p.fecha >= :fechaInicio) AND " +
                "(cast(:fechaFin as timestamp) IS NULL OR p.fecha <= :fechaFin) AND " +
                "(:recetaId IS NULL OR p.receta.id = :recetaId) AND " +
                "(:productoId IS NULL OR p.producto.id = :productoId) AND " +
                "(:sucursalId IS NULL OR p.sucursal.id = :sucursalId)")
        Page<ProduccionEntity> findWithFilters(
                @Param("fechaInicio") LocalDateTime fechaInicio,
                @Param("fechaFin") LocalDateTime fechaFin,
                @Param("recetaId") Integer recetaId,
                @Param("productoId") Integer productoId,
                @Param("sucursalId") Integer sucursalId,
                Pageable pageable);
}
