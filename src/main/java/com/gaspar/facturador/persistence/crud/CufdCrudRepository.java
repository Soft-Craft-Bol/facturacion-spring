package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CufdCrudRepository extends CrudRepository<CufdEntity, Integer> {

    Optional<CufdEntity> findByPuntoVentaAndVigente(PuntoVentaEntity puntoVenta, boolean vigente);

    @Query("SELECT c FROM CufdEntity c WHERE c.puntoVenta = :puntoVenta " +
            "AND c.vigente = false " +
            "AND c.fechaInicio >= :fechaLimite " +  // Solo CUFDs de las últimas 72 horas
            "ORDER BY c.fechaInicio DESC")
    List<CufdEntity> findAnteriores(@Param("puntoVenta") PuntoVentaEntity puntoVenta,
                                    @Param("fechaLimite") LocalDateTime fechaLimite);


    @Query("SELECT COUNT(c) > 0 FROM CufdEntity c WHERE " +
            "c.codigo LIKE CONCAT(:codigo, '%') AND " +
            "c.fechaVigencia > :fecha AND " +
            "c.vigente = true")
    boolean existsByCodigoStartingWithAndFechaVigenciaAfterAndVigente(
            @Param("codigo") String codigo,
            @Param("fecha") LocalDateTime fecha,
            @Param("vigente") boolean vigente);

    @Query("SELECT c.codigoControl FROM CufdEntity c WHERE c.codigo = :cufd")
    Optional<String> findCodigoControlByCufd(String cufd);

}