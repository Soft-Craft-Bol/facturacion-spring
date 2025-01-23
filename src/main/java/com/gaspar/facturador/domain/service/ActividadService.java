package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.domain.repository.IActividadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;


@Service
public class ActividadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IActividadRepository actividadRepository;

    public ActividadService(
        ServicioFacturacionSincronizacion servicioFacturacionSincronizacion,
        IActividadRepository actividadRepository
    ) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.actividadRepository = actividadRepository;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividades respuestaListaActividades = this.obtenerCatalogos(solicitudSincronizacion);
        this.actividadRepository.deleteAll();
        for (ActividadesDto actividadesDto : respuestaListaActividades.getListaActividades()) {
            this.actividadRepository.save(actividadesDto);
        }
    }

    private RespuestaListaActividades obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {

        RespuestaListaActividades respuestaListaActividades = this.servicioFacturacionSincronizacion.sincronizarActividades(solicitudSincronizacion);
        if (!respuestaListaActividades.getTransaccion()) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaListaActividades.getMensajesList()));
        }
        return respuestaListaActividades;
    }

    private String obtenerMensajeServicio(MensajeServicio[] mensajeServicioList) {
        String mensaje = "";
        if (mensajeServicioList != null) {
            for (MensajeServicio mensajeServicio : mensajeServicioList) {
                mensaje += mensajeServicio.getDescripcion() + ". ";
            }
        }
        return mensaje;
    }
}
