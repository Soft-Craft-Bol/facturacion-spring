package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RecetaInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaInsumoCrudRepository extends JpaRepository<RecetaInsumoEntity, Long> {
}
