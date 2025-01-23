package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CufdCrudRepository extends CrudRepository<CufdEntity, Integer> {

    Optional<CufdEntity> findByPuntoVentaAndVigente(PuntoVentaEntity puntoVenta, boolean vigente);
}
