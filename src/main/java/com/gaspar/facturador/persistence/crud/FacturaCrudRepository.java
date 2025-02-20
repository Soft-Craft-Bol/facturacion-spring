package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.FacturaEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface FacturaCrudRepository extends CrudRepository<FacturaEntity, Long> {
    Optional<FacturaEntity> findByCuf(String cuf);
    long countByFechaEmisionBetween(LocalDateTime start, LocalDateTime end);
}
