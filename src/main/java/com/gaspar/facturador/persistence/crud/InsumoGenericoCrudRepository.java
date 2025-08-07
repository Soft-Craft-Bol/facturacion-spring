package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.InsumoGenericoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoGenericoCrudRepository extends JpaRepository<InsumoGenericoEntity, Long> {
    Page<InsumoGenericoEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
