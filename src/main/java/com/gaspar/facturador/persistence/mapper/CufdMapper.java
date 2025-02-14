package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {CufdMapper.class})
public interface CufdMapper {

    CufdEntity toCufdEntity(RespuestaCufd respuestaCufd);
}
