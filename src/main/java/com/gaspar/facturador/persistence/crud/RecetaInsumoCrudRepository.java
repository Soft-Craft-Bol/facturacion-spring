package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RecetaInsumoEntity;
import com.querydsl.core.group.GroupBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecetaInsumoCrudRepository extends JpaRepository<RecetaInsumoEntity, Long> {
    @Query("SELECT ri FROM RecetaInsumoEntity ri WHERE ri.receta.id = :recetaId AND ri.insumoGenerico.id = :insumoGenericoId")
    Optional<RecetaInsumoEntity> findByRecetaIdAndInsumoGenericoId(
            @Param("recetaId") Integer recetaId,
            @Param("insumoGenericoId") Long insumoGenericoId);
}
