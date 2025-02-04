package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.domain.repository.IProductoServicioRepository;
import com.gaspar.facturador.persistence.entity.ProductoServicioEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;


@Service
public class ProductoServicioService {

    private final Logger LOGGER = LoggerFactory.getLogger(SincronizacionCatalogosParametrosService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IProductoServicioRepository productoServicioRepository;

    public ProductoServicioService(
        IProductoServicioRepository productoServicioRepository,
        ServicioFacturacionSincronizacion servicioFacturacionSincronizacion
    ) {
        this.productoServicioRepository = productoServicioRepository;
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaProductos respuestaListaProductos = this.obtenerCatalogos(solicitudSincronizacion);
        productoServicioRepository.deleteAll();
        for (ProductosDto productosDto : respuestaListaProductos.getListaCodigos()) {
            productoServicioRepository.save(productosDto);
        }
    }

    private RespuestaListaProductos obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaProductos respuestaListaProductos = servicioFacturacionSincronizacion
                .sincronizarListaProductosServicios(solicitudSincronizacion);
        if (!respuestaListaProductos.getTransaccion()) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaListaProductos.getMensajesList()));
        }
        return respuestaListaProductos;
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
