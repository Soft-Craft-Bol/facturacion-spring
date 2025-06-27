package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CompraInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraInsumoCrudRepository extends JpaRepository<CompraInsumoEntity, Long> {
}
