package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.EventoSignificativoEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IEventoSignificativoRepository extends JpaRepository<EventoSignificativoEntity, Long> {

    @Query("SELECT e FROM EventoSignificativoEntity e WHERE " +
            "e.puntoVenta = :puntoVenta AND " +
            "e.vigente = true AND " +
            "e.fechaRegistro >= :fechaLimite " +
            "ORDER BY e.fechaInicio DESC")
    List<EventoSignificativoEntity> findVigentesByPuntoVenta(
            PuntoVentaEntity puntoVenta,
            LocalDateTime fechaLimite);

    @Query("SELECT e FROM EventoSignificativoEntity e WHERE " +
            "e.codigoRecepcionEvento = :codigoRecepcionEvento AND " +
            "e.puntoVenta = :puntoVenta AND " +
            "e.vigente = true")
    Optional<EventoSignificativoEntity> findByCodigoRecepcionEventoAndPuntoVenta(
            Long codigoRecepcionEvento,
            PuntoVentaEntity puntoVenta);

    @Query("SELECT e FROM EventoSignificativoEntity e WHERE " +
            "e.codigoRecepcionPaquete = :codigoRecepcion AND " +
            "e.puntoVenta = :puntoVenta")
    Optional<EventoSignificativoEntity> findByCodigoRecepcionPaqueteAndPuntoVenta(
            String codigoRecepcion,
            PuntoVentaEntity puntoVenta);
}