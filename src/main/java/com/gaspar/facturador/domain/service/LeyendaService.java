package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.*;
import com.gaspar.facturador.domain.repository.ILeyendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeyendaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacionCatalogosParametrosService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final ILeyendaRepository leyendaRepository;

    public LeyendaService(
            ILeyendaRepository leyendaRepository,
            ServicioFacturacionSincronizacion servicioFacturacionSincronizacion
    ) {
        this.leyendaRepository = leyendaRepository;
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas respuestaLeyendas = this.obtenerCatalogos(solicitudSincronizacion);
//        leyendaRepository.deleteAll();
//        for (ParametricaLeyendasDto actividadesDto : respuestaLeyendas.getListaLeyendas()) {
//            leyendaRepository.save(actividadesDto);
//        }
    }

    private RespuestaListaParametricasLeyendas obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas respuestaLeyendas = servicioFacturacionSincronizacion
                .sincronizarListaLeyendasFactura(solicitudSincronizacion);

        if (Boolean.FALSE.equals(respuestaLeyendas.isTransaccion())) {
            String mensajeError = obtenerMensajeServicio(respuestaLeyendas.getMensajesList());
            LOGGER.error("Error al sincronizar lista de leyendas: {}", mensajeError);
            throw new RemoteException("Error en sincronización: " + mensajeError);
        }

        return respuestaLeyendas;
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
