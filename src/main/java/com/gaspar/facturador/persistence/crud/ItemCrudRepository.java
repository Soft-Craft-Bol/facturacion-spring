package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCrudRepository extends JpaRepository<ItemEntity, Integer>, JpaSpecificationExecutor<ItemEntity> {
    @EntityGraph(attributePaths = {"sucursalItems.sucursal", "promocionItems", "recetas"})
    @Query("SELECT i FROM ItemEntity i WHERE LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<ItemEntity> findItemsWithSucursales(@Param("searchTerm") String searchTerm, Pageable pageable);

    @EntityGraph(attributePaths = {"sucursalItems.sucursal"})
    @Override
    Page<ItemEntity> findAll(Specification<ItemEntity> spec, Pageable pageable);

    Optional<ItemEntity> findByCodigo(String codigo);
    // Mantén el método original para compatibilidad
    Page<ItemEntity> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);

    @Query("SELECT i FROM ItemEntity i WHERE i.categoria.id = :categoriaId")
    List<ItemEntity> findByCategoriaId(@Param("categoriaId") Integer categoriaId);
}