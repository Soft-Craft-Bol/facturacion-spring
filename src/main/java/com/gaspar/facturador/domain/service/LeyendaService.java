package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.domain.repository.ILeyendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;


@Service
public class LeyendaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacionCatalogosParametrosService.class);

    private ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private ILeyendaRepository leyendaRepository;

    public LeyendaService(
        ILeyendaRepository leyendaRepository,
        ServicioFacturacionSincronizacion servicioFacturacionSincronizacion
    ) {
        this.leyendaRepository = leyendaRepository;
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas respuestaLeyendas = this.obtenerCatalogos(solicitudSincronizacion);
        leyendaRepository.deleteAll();
        for (ParametricaLeyendasDto actividadesDto : respuestaLeyendas.getListaLeyendas()) {
            leyendaRepository.save(actividadesDto);
        }
    }

    private RespuestaListaParametricasLeyendas obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas respuestaLeyendas = servicioFacturacionSincronizacion
                .sincronizarListaLeyendasFactura(solicitudSincronizacion);
        if (!respuestaLeyendas.getTransaccion()) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaLeyendas.getMensajesList()));
        }
        return respuestaLeyendas;
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
