package com.gaspar.facturador.utils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class DateUtil {

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime) {
        try {
            GregorianCalendar gCalendar = GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir LocalDateTime a XMLGregorianCalendar", e);
        }
    }
}
