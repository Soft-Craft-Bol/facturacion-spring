package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.RespuestaListaParametricas;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.SolicitudSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.MensajeServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;

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

        if (Boolean.FALSE.equals(respuesta.isTransaccion())) {
            List<MensajeServicio> mensajesList = respuesta.getMensajesList();

            // Convertir la lista a un array antes de usarla si es necesario
            MensajeServicio[] mensajesArray = mensajesList.toArray(new MensajeServicio[0]);

            LOGGER.error("Error al sincronizar eventos significativos: {}", (Object) mensajesArray);
            throw new RemoteException("Error en sincronización: " + mensajesList);
        }

        LOGGER.info("Eventos significativos sincronizados correctamente: {}", respuesta.getListaCodigos());

        return respuesta;
    }
}
