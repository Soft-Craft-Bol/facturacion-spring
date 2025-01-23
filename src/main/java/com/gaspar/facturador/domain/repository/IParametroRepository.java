package com.gaspar.facturador.domain.repository;

import bo.gob.impuestos.siat.ParametricasDto;
import com.gaspar.facturador.commons.ParametricaEnum;


public interface IParametroRepository {

    void save(ParametricasDto parametricasDto, ParametricaEnum parametricaEnum);
    void deleteAll();
}
