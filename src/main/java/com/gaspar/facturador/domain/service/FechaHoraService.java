package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.RespuestaFechaHora;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.SolicitudSincronizacion;
import jakarta.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FechaHoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FechaHoraService.class);
    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;

    public FechaHoraService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public RespuestaFechaHora obtenerFechaHora(SolicitudSincronizacion solicitudSincronizacion) {
        try {
            LOGGER.info("Iniciando sincronización de fecha y hora");

            RespuestaFechaHora respuesta = servicioFacturacionSincronizacion.sincronizarFechaHora(solicitudSincronizacion);

            if (respuesta == null) {
                throw new RuntimeException("La respuesta del servicio es nula");
            }

            LOGGER.info("Sincronización completada. Fecha hora: {}", respuesta.getFechaHora());
            return respuesta;

        } catch (WebServiceException e) {
            LOGGER.error("Error en la comunicación con el servicio SOAP", e);
            throw new RuntimeException("Error en la comunicación con el servicio de sincronización", e);
        } catch (Exception e) {
            LOGGER.error("Error al procesar la sincronización", e);
            throw new RuntimeException("Error al procesar la solicitud de sincronización", e);
        }
    }
}