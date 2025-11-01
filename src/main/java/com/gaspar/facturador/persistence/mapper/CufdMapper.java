package com.gaspar.facturador.persistence.mapper;

import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring", uses = {CufdMapper.class})
public interface CufdMapper {

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "codigoControl", source = "codigoControl")
    @Mapping(target = "fechaVigencia", expression = "java(convertXmlGregorianToLocalDateTime(respuestaCufd.getFechaVigencia()))")
    @Mapping(target = "direccion", source = "direccion") // Este es el campo que falta
    CufdEntity toCufdEntity(RespuestaCufd respuestaCufd);

    default LocalDateTime convertXmlGregorianToLocalDateTime(XMLGregorianCalendar xmlGregorian) {
        if (xmlGregorian == null) return null;
        return xmlGregorian.toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime();
    }
}
