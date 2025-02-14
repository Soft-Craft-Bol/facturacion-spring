package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCuis;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {CuisMapper.class})
public interface CuisMapper {

    CuisEntity toCuisEntity(RespuestaCuis respuestaCuis);
}
