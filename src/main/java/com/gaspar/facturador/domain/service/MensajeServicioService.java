package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaListaParametricas;
import bo.gob.impuestos.siat.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.SolicitudSincronizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class MensajeServicioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MensajeServicioService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;

    public MensajeServicioService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public RespuestaListaParametricas obtenerMensajesServicios(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        LOGGER.info("Enviando solicitud de sincronización de mensajes de servicios: {}", solicitudSincronizacion);

        RespuestaListaParametricas respuestaListaParametricas = servicioFacturacionSincronizacion.sincronizarListaMensajesServicios(solicitudSincronizacion);

        if (!respuestaListaParametricas.getTransaccion()) {
            LOGGER.error("Error al sincronizar mensajes de servicios: {}", respuestaListaParametricas.getMensajesList());
            throw new RemoteException("Error en sincronización: " + respuestaListaParametricas.getMensajesList());
        }

        LOGGER.info("Mensajes de servicios sincronizados correctamente: {}", (Object) respuestaListaParametricas.getListaCodigos());

        return respuestaListaParametricas;
    }
}
