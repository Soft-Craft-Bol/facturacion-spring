package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.RespuestaCuis;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;

import java.util.Optional;


public interface ICuisRepository {

    void save(RespuestaCuis respuestaCuis, PuntoVentaEntity puntoVenta);

    Optional<CuisEntity> findActual(PuntoVentaEntity puntoVenta);
}
