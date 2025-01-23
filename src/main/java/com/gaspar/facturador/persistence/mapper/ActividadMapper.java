package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ActividadesDto;
import com.gaspar.facturador.persistence.entity.ActividadEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {ActividadMapper.class})
public interface ActividadMapper {

    ActividadEntity toActividadEntity(ActividadesDto actividadesDto);
}
