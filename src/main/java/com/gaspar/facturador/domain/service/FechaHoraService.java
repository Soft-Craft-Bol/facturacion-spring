package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.RespuestaFechaHora;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.SolicitudSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.MensajeServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

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

        if (Boolean.FALSE.equals(respuestaFechaHora.isTransaccion())) {
            List<MensajeServicio> mensajesList = respuestaFechaHora.getMensajesList();

            // Manejo seguro de mensajes
            String mensajesError = (mensajesList != null && !mensajesList.isEmpty())
                    ? mensajesList.stream().map(MensajeServicio::toString).collect(Collectors.joining(", "))
                    : "Error desconocido";

            LOGGER.error("Error al sincronizar fecha y hora: {}", mensajesError);
            throw new RemoteException("Error en sincronización: " + mensajesError);
        }

        LOGGER.info("Fecha y hora sincronizada correctamente: {}", respuestaFechaHora.getFechaHora());

        return respuestaFechaHora;
    }
}
