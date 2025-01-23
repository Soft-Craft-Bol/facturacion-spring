package com.gaspar.facturador.domain.helpers;

import com.gaspar.facturador.domain.DatosCUF;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Utils {

    public static XMLGregorianCalendar localDateTimeToXMLGregorianCalendar(LocalDateTime dateTime) throws DatatypeConfigurationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        String formattedDate = dateTime.format(formatter);
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(formattedDate);
        return calendar;
    }

    public static LocalDateTime xMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar calendar) {
        if (calendar != null) {
            ZonedDateTime zonedDateTime = calendar.toGregorianCalendar()
                    .toZonedDateTime();/*  w ww .j a  v  a  2  s  .com*/
            return ZonedDateTime.ofInstant(zonedDateTime.toInstant(),
                    ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }

    public static String obtenerCUF(DatosCUF datosFacturaCUF, String codigoControl) {
        String datosConCeros = rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getNitEmisor()), (short) 13);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formatDateTime = datosFacturaCUF.getFechaHora().format(formatter);
        datosConCeros += Utils.rellenaCerosIzquierda(formatDateTime, (short) 17);

        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getSucursal()), (short) 4);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getModalidad()), (short) 1);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoEmision()), (short) 1);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoFacturaDocumentoAjuste()), (short) 1);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getTipoDocumentoSector()), (short) 2);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getNumeroFactura()), (short) 10);
        datosConCeros += Utils.rellenaCerosIzquierda(String.valueOf(datosFacturaCUF.getPuntoVenta()), (short) 4);

        String codigoModulo11 = datosConCeros + calculaDigitoMod11(datosConCeros, 1, 9, false);
        String codigoBase16 = base16(codigoModulo11);
        String cuf = codigoBase16 + codigoControl;

        return cuf;
    }

    public static String rellenaCerosIzquierda(String pString, short pMaxChar) {
        String vNewString = pString;
        if (pString.length() < pMaxChar) {
            for (int i = pString.length(); i < pMaxChar; i++) {
                vNewString = "0" + vNewString;
            }
        }
        return vNewString;
    }

    public static String calculaDigitoMod11(String cadena, int numDig, int limMult, boolean x10) {
        int mult, suma, i, n, dig;
        if (!x10) numDig = 1;
        for (n = 1; n <= numDig; n++) {
            suma = 0;
            mult = 2;
            for (i = cadena.length() - 1; i >= 0; i--) {
                suma += (mult * Integer.parseInt(cadena.substring(i, i + 1)));
                if (++mult > limMult) mult = 2;
            }
            if (x10) {
                dig = ((suma * 10) % 11) % 10;
            } else {
                dig = suma % 11;
            }

            if (dig == 10) {
                cadena += "1";
            }

            if (dig == 11) {
                cadena += "0";
            }

            if (dig < 10) {
                cadena += String.valueOf(dig);
            }
        }
        return cadena.substring(cadena.length() - numDig);
    }

    public static String base16(String pString) {
        BigInteger vValor = new BigInteger(pString);
        return vValor.toString(16).toUpperCase();
    }

    public static String obtenerSHA2(byte[] archivo) {
        String vSha2 = obtenerHash(archivo,"SHA-256");
        return vSha2;
    }

    public static String obtenerHash(byte[] pArchivo, String algorithm) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(pArchivo);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (Exception e) {
            System.out.println("Error generando Hash");
        }
        return hashValue;
    }
}
