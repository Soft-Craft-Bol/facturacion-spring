package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.FacturaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;


public interface FacturaCrudRepository extends CrudRepository<FacturaEntity, Long> {
    Optional<FacturaEntity> findByCuf(String cuf);
    long countByFechaEmisionBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT SUM(fd.subTotal) FROM FacturaEntity f JOIN f.detalleList fd WHERE f.fechaEmision BETWEEN :start AND :end")
    double sumSubtotalByFechaEmisionBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
