package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.VentasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

public interface VentaCrudRepository extends JpaRepository<VentasEntity, Long> {
    default Page<VentasEntity> findByFecha(LocalDate fecha, Pageable pageable) {
        Date fechaInicio = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return findByFechaBetween(fechaInicio, fechaFin, pageable);
    }

    Page<VentasEntity> findByFechaBetween(Date fechaInicio, Date fechaFin, Pageable pageable);
}