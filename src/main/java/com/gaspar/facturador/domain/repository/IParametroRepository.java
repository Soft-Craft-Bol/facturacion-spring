package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ParametricasDto;
import com.gaspar.facturador.commons.ParametricaEnum;


public interface IParametroRepository {

    void save(ParametricasDto parametricasDto, ParametricaEnum parametricaEnum);
    void deleteAll();
}
