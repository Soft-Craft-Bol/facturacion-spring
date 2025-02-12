package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ParametricasDto;
import com.gaspar.facturador.persistence.entity.ParametroEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {ParametroMapper.class})
public interface ParametroMapper {

    ParametroEntity toParametroEntity(ParametricasDto parametricasDto);
}
