package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.DetalleProduccionInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleProduccionInsumoCrudRepository extends JpaRepository<DetalleProduccionInsumoEntity, Long> {
}