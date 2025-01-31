package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaFechaHora;
import bo.gob.impuestos.siat.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.SolicitudSincronizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class FechaHoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FechaHoraService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;

    public FechaHoraService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public RespuestaFechaHora obtenerFechaHora(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        LOGGER.info("Enviando solicitud de sincronización de fecha y hora: {}", solicitudSincronizacion);

        RespuestaFechaHora respuestaFechaHora = servicioFacturacionSincronizacion.sincronizarFechaHora(solicitudSincronizacion);

        if (!respuestaFechaHora.getTransaccion()) {
            LOGGER.error("Error al sincronizar fecha y hora: {}", respuestaFechaHora.getMensajesList());
            throw new RemoteException("Error en sincronización: " + respuestaFechaHora.getMensajesList());
        }

        LOGGER.info("Fecha y hora sincronizada correctamente: {}", respuestaFechaHora.getFechaHora());

        return respuestaFechaHora;
    }
}
