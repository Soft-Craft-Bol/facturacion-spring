package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.MovimientoProduccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoProduccionRepository extends JpaRepository<MovimientoProduccionEntity, Long> {
}
