package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.*;
import com.gaspar.facturador.domain.repository.IActividadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

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
//        this.actividadRepository.deleteAll();
//        for (ActividadesDto actividadesDto : respuestaListaActividades.getListaActividades()) {
//            this.actividadRepository.save(actividadesDto);
//        }
    }

    private RespuestaListaActividades obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividades respuestaListaActividades = this.servicioFacturacionSincronizacion.sincronizarActividades(solicitudSincronizacion);

        if (!Boolean.TRUE.equals(respuestaListaActividades.isTransaccion())) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaListaActividades.getMensajesList()));
        }
        return respuestaListaActividades;
    }

    private String obtenerMensajeServicio(List<MensajeServicio> mensajeServicioList) {
        if (mensajeServicioList == null || mensajeServicioList.isEmpty()) {
            return "No se encontraron mensajes de error.";
        }

        return mensajeServicioList.stream()
                .map(MensajeServicio::getDescripcion)
                .collect(Collectors.joining(". ")) + ".";
    }
}
