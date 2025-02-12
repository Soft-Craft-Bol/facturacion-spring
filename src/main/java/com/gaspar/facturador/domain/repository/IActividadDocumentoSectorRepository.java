package com.gaspar.facturador.domain.repository;


import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ActividadesDocumentoSectorDto;

public interface IActividadDocumentoSectorRepository {

    void save(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto);

    void deleteAll();
}
