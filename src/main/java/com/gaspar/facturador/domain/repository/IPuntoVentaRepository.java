package com.gaspar.facturador.domain.repository;

import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;

import java.util.Optional;


public interface IPuntoVentaRepository {

    Optional<PuntoVentaEntity> findById(Integer id);

    Optional<PuntoVentaEntity> findByCodigo(Integer codigo);
}
