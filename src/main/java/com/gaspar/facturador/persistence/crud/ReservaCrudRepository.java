package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.ReservaEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaCrudRepository extends CrudRepository<ReservaEntity, Integer> {
    List<ReservaEntity> findByEstado(String pendiente);
    List<ReservaEntity> findByFechaReservaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<ReservaEntity> findByEstadoAndFechaReservaBetween(String estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
