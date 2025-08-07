package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoGenericoEntity;
import com.gaspar.facturador.persistence.entity.RecetasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecetasCrudRepository extends JpaRepository<RecetasEntity, Integer> {
    List<RecetasEntity> findByProductoId(Integer productoId);
    @Query("SELECT r FROM RecetasEntity r LEFT JOIN FETCH r.recetaInsumos WHERE r.id = :id")
    Optional<RecetasEntity> findByIdWithInsumos(@Param("id") Integer id);
    @Query("SELECT DISTINCT r FROM RecetasEntity r LEFT JOIN FETCH r.recetaInsumos")
    List<RecetasEntity> findAllWithInsumos();
    List<RecetasEntity> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT r FROM RecetasEntity r LEFT JOIN FETCH r.recetaInsumos " +
            "WHERE (:nombre IS NULL OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', CAST(:nombre AS string), '%'))) " +
            "AND (:productoId IS NULL OR r.producto.id = :productoId)")
    Page<RecetasEntity> findByFilters(
            @Param("nombre") String nombre,
            @Param("productoId") Integer productoId,
            Pageable pageable);

    // Opción 2: Usando el nombre del método convencional
    @EntityGraph(attributePaths = {"recetaInsumos", "recetaInsumos.insumoGenerico"})
    Optional<RecetasEntity> findById(Integer id);

    @Query("SELECT DISTINCT ig FROM InsumoGenericoEntity ig " +
            "LEFT JOIN FETCH ig.insumosAsociados iad " +
            "LEFT JOIN FETCH iad.insumo " +
            "WHERE ig IN :insumosGenericos")
    List<InsumoGenericoEntity> findInsumosEspecificos(@Param("insumosGenericos") List<InsumoGenericoEntity> insumosGenericos);

    @Query("SELECT DISTINCT r FROM RecetasEntity r " +
            "LEFT JOIN FETCH r.recetaInsumos ri " +
            "LEFT JOIN FETCH ri.insumoGenerico ig " +
            "LEFT JOIN FETCH ig.insumosAsociados iad " +
            "LEFT JOIN FETCH iad.insumo " +
            "WHERE (:nombre IS NULL OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', CAST(:nombre AS string), '%'))) " +
            "AND (:productoId IS NULL OR r.producto.id = :productoId)")
    Page<RecetasEntity> findByFiltersWithDetails(
            @Param("nombre") String nombre,
            @Param("productoId") Integer productoId,
            Pageable pageable);

    @Query("SELECT DISTINCT r FROM RecetasEntity r " +
            "LEFT JOIN FETCH r.recetaInsumos ri " +
            "LEFT JOIN FETCH ri.insumoGenerico " +
            "WHERE r.id IN :ids")
    List<RecetasEntity> findByIdInWithInsumos(@Param("ids") List<Integer> ids);
}
