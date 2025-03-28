package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;

import java.util.List;
import java.util.Optional;



public interface ICufdRepository {

    void save(RespuestaCufd respuestaCufd, PuntoVentaEntity puntoVenta);

    Optional<CufdEntity> findActual(PuntoVentaEntity puntoVenta);

    List<CufdEntity> findAnteriores(PuntoVentaEntity puntoVenta);
}
