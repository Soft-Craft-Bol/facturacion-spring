package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.domain.repository.IActividadDocumentoSectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ActividadDocumentoSectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacionCatalogosParametrosService.class);

    private ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private IActividadDocumentoSectorRepository actividadDocumentoSectorRepository;

    public ActividadDocumentoSectorService(
            IActividadDocumentoSectorRepository actividadDocumentoSectorRepository,
        ServicioFacturacionSincronizacion servicioFacturacionSincronizacion
    ) {
        this.actividadDocumentoSectorRepository = actividadDocumentoSectorRepository;
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaListaActividades = this.obtenerActividadesDocumentoSector(solicitudSincronizacion);
        this.actividadDocumentoSectorRepository.deleteAll();
        for (ActividadesDocumentoSectorDto actividadesDocumentoSectorDto : respuestaListaActividades.getListaActividadesDocumentoSector()) {
            actividadDocumentoSectorRepository.save(actividadesDocumentoSectorDto);
        }
    }

    private RespuestaListaActividadesDocumentoSector obtenerActividadesDocumentoSector(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaActividadesDocumentoSector = servicioFacturacionSincronizacion
                .sincronizarListaActividadesDocumentoSector(solicitudSincronizacion);
        if (!respuestaActividadesDocumentoSector.getTransaccion()) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaActividadesDocumentoSector.getMensajesList()));
        }
        return respuestaActividadesDocumentoSector;
    }

    private String  obtenerMensajeServicio(MensajeServicio[] mensajeServicioList) {
        String mensaje = "";
        if (mensajeServicioList != null) {
            for (MensajeServicio mensajeServicio : mensajeServicioList) {
                mensaje += mensajeServicio.getDescripcion() + ". ";
            }
        }
        return mensaje;
    }
}
