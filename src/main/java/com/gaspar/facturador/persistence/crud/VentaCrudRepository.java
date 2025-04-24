package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.dto.TotalVentasPorDiaDTO;
import com.gaspar.facturador.persistence.entity.VentasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;

public interface VentaCrudRepository extends JpaRepository<VentasEntity, Long> {
    default Page<VentasEntity> findByFecha(LocalDate fecha, Pageable pageable) {
        Date fechaInicio = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return findByFechaBetween(fechaInicio, fechaFin, pageable);
    }

    Page<VentasEntity> findByFechaBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    @Query(value = """
    SELECT 
        to_char(fecha, 'YYYY-MM-DD') as fecha, 
        SUM(monto) as total
    FROM ventas_entity
    GROUP BY to_char(fecha, 'YYYY-MM-DD')
    ORDER BY fecha
    """, nativeQuery = true)
    List<TotalVentasPorDiaDTO> findVentasAgrupadasPorDia();
}