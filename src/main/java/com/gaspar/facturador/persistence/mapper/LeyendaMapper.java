package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ParametricaLeyendasDto;
import com.gaspar.facturador.persistence.entity.LeyendaFacturaEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {LeyendaMapper.class})
public interface LeyendaMapper {

    LeyendaFacturaEntity toLeyendaFacturaEntity(ParametricaLeyendasDto parametricaLeyendasDto);
}
