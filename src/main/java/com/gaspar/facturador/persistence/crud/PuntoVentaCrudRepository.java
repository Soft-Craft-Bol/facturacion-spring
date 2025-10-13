package com.gaspar.facturador.persistence.crud;

import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PuntoVentaCrudRepository extends JpaRepository<PuntoVentaEntity, Integer> {

    Optional<PuntoVentaEntity> findByCodigo(Integer codigo);
    List<PuntoVentaEntity> findBySucursalId(Integer idSucursal);
    long count();
}
