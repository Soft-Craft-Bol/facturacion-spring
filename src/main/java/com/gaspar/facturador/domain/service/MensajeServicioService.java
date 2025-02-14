package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.RespuestaListaParametricas;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.SolicitudSincronizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServicioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MensajeServicioService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;

    public MensajeServicioService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public RespuestaListaParametricas obtenerMensajesServicios(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        LOGGER.info("Enviando solicitud de sincronización de mensajes de servicios: {}", solicitudSincronizacion);

        RespuestaListaParametricas respuestaListaParametricas = servicioFacturacionSincronizacion
                .sincronizarListaMensajesServicios(solicitudSincronizacion);

        if (Boolean.FALSE.equals(respuestaListaParametricas.isTransaccion())) {
            List<MensajeServicio> mensajesList = respuestaListaParametricas.getMensajesList();
            String mensajeError = obtenerMensajeServicio(mensajesList);
            LOGGER.error("Error al sincronizar mensajes de servicios: {}", mensajeError);
            throw new RemoteException("Error en sincronización: " + mensajeError);
        }

        LOGGER.info("Mensajes de servicios sincronizados correctamente: {}", respuestaListaParametricas.getListaCodigos());

        return respuestaListaParametricas;
    }

    private String obtenerMensajeServicio(List<MensajeServicio> mensajeServicioList) {
        if (mensajeServicioList == null || mensajeServicioList.isEmpty()) {
            return "No se recibieron mensajes de error.";
        }

        return mensajeServicioList.stream()
                .map(MensajeServicio::getDescripcion)
                .collect(Collectors.joining(". "));
    }
}
