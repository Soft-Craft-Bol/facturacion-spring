package com.gaspar.facturador.domain.repository;


import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ActividadesDto;

public interface IActividadRepository {

    void save(ActividadesDto actividadesDto);
    void deleteAll();
}
