package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.*;
import com.gaspar.facturador.domain.repository.IProductoServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductoServicioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoServicioService.class);

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

        if (respuestaListaProductos == null || respuestaListaProductos.getListaCodigos() == null) {
            LOGGER.error("No se obtuvieron productos/servicios de la sincronización.");
            return;
        }

//        productoServicioRepository.deleteAll();
//        for (ProductosDto productosDto : respuestaListaProductos.getListaCodigos()) {
//            productoServicioRepository.save(productosDto);
//        }
    }

    private RespuestaListaProductos obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaProductos respuestaListaProductos = servicioFacturacionSincronizacion
                .sincronizarListaProductosServicios(solicitudSincronizacion);

        if (Boolean.FALSE.equals(respuestaListaProductos.isTransaccion())) {
            String mensajeError = obtenerMensajeServicio(respuestaListaProductos.getMensajesList());
            LOGGER.error("Error en la sincronización de productos/servicios: {}", mensajeError);
        }

        return respuestaListaProductos;
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
