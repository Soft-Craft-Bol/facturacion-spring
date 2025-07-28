package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.MetodoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetodoPagoRepository extends JpaRepository<MetodoPagoEntity, Long> {
    Optional<MetodoPagoEntity> findByCodigoIgnoreCase(String codigo);
}
