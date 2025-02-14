package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.*;
import com.gaspar.facturador.domain.repository.IActividadDocumentoSectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadDocumentoSectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadDocumentoSectorService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IActividadDocumentoSectorRepository actividadDocumentoSectorRepository;

    public ActividadDocumentoSectorService(
            IActividadDocumentoSectorRepository actividadDocumentoSectorRepository,
            ServicioFacturacionSincronizacion servicioFacturacionSincronizacion
    ) {
        this.actividadDocumentoSectorRepository = actividadDocumentoSectorRepository;
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaListaActividades = this.obtenerActividadesDocumentoSector(solicitudSincronizacion);
        actividadDocumentoSectorRepository.deleteAll();

        for (ActividadesDocumentoSectorDto actividadesDocumentoSectorDto : respuestaListaActividades.getListaActividadesDocumentoSector()) {
            actividadDocumentoSectorRepository.save(actividadesDocumentoSectorDto);
        }
    }

    private RespuestaListaActividadesDocumentoSector obtenerActividadesDocumentoSector(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaActividadesDocumentoSector = servicioFacturacionSincronizacion
                .sincronizarListaActividadesDocumentoSector(solicitudSincronizacion);

        if (!Boolean.TRUE.equals(respuestaActividadesDocumentoSector.isTransaccion())) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaActividadesDocumentoSector.getMensajesList()));
        }

        return respuestaActividadesDocumentoSector;
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
