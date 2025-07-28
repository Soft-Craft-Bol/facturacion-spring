package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.MovimientoMermaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoMermaRepository extends JpaRepository<MovimientoMermaEntity, Long>, JpaSpecificationExecutor<MovimientoMermaEntity> {
}