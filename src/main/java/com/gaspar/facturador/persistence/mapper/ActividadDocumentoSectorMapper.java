package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ActividadesDocumentoSectorDto;
import com.gaspar.facturador.persistence.entity.ActividadDocumentoSectorEntity;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {ActividadDocumentoSectorMapper.class})
public interface ActividadDocumentoSectorMapper {

    ActividadDocumentoSectorEntity toActividadDocumentoSectorEntity(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto);
}
