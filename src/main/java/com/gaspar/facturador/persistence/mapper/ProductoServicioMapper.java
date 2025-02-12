package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ProductosDto;
import com.gaspar.facturador.persistence.entity.ProductoServicioEntity;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {ProductoServicioMapper.class})
public interface ProductoServicioMapper {

    ProductoServicioEntity toActividadEntity(ProductosDto actividadDto);
}
