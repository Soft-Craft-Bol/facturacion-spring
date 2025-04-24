package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// En ItemCrudRepository
public interface ItemCrudRepository extends JpaRepository<ItemEntity, Integer> {
    Optional<ItemEntity> findByCodigo(String codigo);

    @Query("SELECT DISTINCT i FROM ItemEntity i LEFT JOIN FETCH i.sucursalItems si LEFT JOIN FETCH si.sucursal " +
            "WHERE LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<ItemEntity> findItemsWithSucursales(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Mantén el método original para compatibilidad
    Page<ItemEntity> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);
}