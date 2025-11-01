package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.DespachoInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespachoInsumoRepository extends JpaRepository<DespachoInsumoEntity, Long> {
}
