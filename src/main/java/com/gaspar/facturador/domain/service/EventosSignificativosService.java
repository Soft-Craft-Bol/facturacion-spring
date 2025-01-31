package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaListaParametricas;
import bo.gob.impuestos.siat.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.SolicitudSincronizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class EventosSignificativosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventosSignificativosService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;

    public EventosSignificativosService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public RespuestaListaParametricas obtenerEventosSignificativos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        LOGGER.info("Enviando solicitud de sincronización de eventos significativos: {}", solicitudSincronizacion);

        RespuestaListaParametricas respuesta = servicioFacturacionSincronizacion.sincronizarParametricaEventosSignificativos(solicitudSincronizacion);

        if (Boolean.FALSE.equals(respuesta.getTransaccion())) {
            LOGGER.error("Error al sincronizar eventos significativos: {}", (Object) respuesta.getMensajesList());
            throw new RemoteException("Error en sincronización: " + respuesta.getMensajesList());
        }

        LOGGER.info("Eventos significativos sincronizados correctamente: {}", (Object) respuesta.getListaCodigos());

        return respuesta;
    }
}
