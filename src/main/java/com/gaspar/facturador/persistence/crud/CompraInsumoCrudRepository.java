package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CompraInsumoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraInsumoCrudRepository extends JpaRepository<CompraInsumoEntity, Long> {
    Page<CompraInsumoEntity> findAll(Specification<CompraInsumoEntity> spec, Pageable pageable);
}
