package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CierreCajasEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CierreCajaRepository extends JpaRepository<CierreCajasEnity, Long> {

    // Método para encontrar cierre abierto por caja
    Optional<CierreCajasEnity> findByCajaIdAndFechaCierreIsNull(Long cajaId);

    // Método para verificar existencia de cierre abierto
    boolean existsByCajaIdAndFechaCierreIsNull(Long cajaId);

    // Otros métodos existentes...
    List<CierreCajasEnity> findByCajaIdOrderByFechaCierreDesc(Long cajaId);

    @Query("SELECT c FROM CierreCajasEnity c WHERE c.caja.id = :cajaId " +
            "AND c.fechaCierre BETWEEN :fechaInicio AND :fechaFin")
    List<CierreCajasEnity> findByCajaAndFecha(
            @Param("cajaId") Long cajaId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}