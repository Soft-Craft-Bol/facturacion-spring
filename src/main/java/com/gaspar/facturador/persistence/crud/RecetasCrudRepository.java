package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.RecetasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetasCrudRepository extends JpaRepository<RecetasEntity, Integer> {
}
