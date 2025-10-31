package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.AjusteInventarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AjusteInventarioRepository extends JpaRepository<AjusteInventarioEntity, Integer>,
        JpaSpecificationExecutor<AjusteInventarioEntity> {

    // Método genérico usando Specification
    Page<AjusteInventarioEntity> findAll(Specification<AjusteInventarioEntity> spec, Pageable pageable);
}