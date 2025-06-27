package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<GastoEntity, Long> {
}
