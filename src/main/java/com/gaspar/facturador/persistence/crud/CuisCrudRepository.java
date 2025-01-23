package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CuisCrudRepository extends CrudRepository<CuisEntity, Integer> {

    Optional<CuisEntity> findByPuntoVentaAndVigente(PuntoVentaEntity puntoVenta, boolean vigente);
}
