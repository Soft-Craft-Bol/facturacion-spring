package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RecetasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecetasCrudRepository extends JpaRepository<RecetasEntity, Integer> {
    List<RecetasEntity> findByProductoId(Integer productoId);

    // Encontrar receta con sus insumos (usando JOIN FETCH para evitar N+1)
    @Query("SELECT r FROM RecetasEntity r LEFT JOIN FETCH r.recetaInsumos WHERE r.id = :id")
    Optional<RecetasEntity> findByIdWithInsumos(@Param("id") Integer id);

    // Listar todas las recetas con sus insumos
    @Query("SELECT DISTINCT r FROM RecetasEntity r LEFT JOIN FETCH r.recetaInsumos")
    List<RecetasEntity> findAllWithInsumos();

    // Buscar recetas por nombre (con insensibilidad a mayúsculas)
    List<RecetasEntity> findByNombreContainingIgnoreCase(String nombre);
}
