package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaFechaHora;
import bo.gob.impuestos.siat.RespuestaListaParametricas;
import bo.gob.impuestos.siat.SolicitudSincronizacion;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;



@Service
public class SincronizacionCatalogosParametrosService {

    private final AppConfig appConfig;
    private final FechaHoraService fechaHoraService;
    private final ActividadService actividadService;
    private final ActividadDocumentoSectorService actividadDocumentoSectorService;
    private final LeyendaService leyendaService;
    private final ProductoServicioService productoServicioService;
    private final ParametroService parametroService;
    private final MensajeServicioService mensajeServicioService;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICuisRepository cuisRepository;

    public SincronizacionCatalogosParametrosService(
            AppConfig appConfig, FechaHoraService fechaHoraService,
            ActividadService actividadService,
            ActividadDocumentoSectorService actividadDocumentoSectorService,
            LeyendaService leyendaService,
            ProductoServicioService productoServicioService,
            ParametroService parametroService, MensajeServicioService mensajeServicioService,
            IPuntoVentaRepository puntoVentaRepository,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.fechaHoraService = fechaHoraService;
        this.actividadService = actividadService;
        this.actividadDocumentoSectorService = actividadDocumentoSectorService;
        this.leyendaService = leyendaService;
        this.productoServicioService = productoServicioService;
        this.parametroService = parametroService;
        this.mensajeServicioService = mensajeServicioService;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cuisRepository = cuisRepository;
    }

    public void sincronizarCatalogos(Long idPuntoVenta) throws RemoteException {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = this.cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        SolicitudSincronizacion solicitudSincronizacion = this.obtenerSolicitud(puntoVenta.get(), cuis.get());

        this.actividadService.guardarCatalogos(solicitudSincronizacion);
        this.actividadDocumentoSectorService.guardarCatalogos(solicitudSincronizacion);
        this.leyendaService.guardarCatalogos(solicitudSincronizacion);
        this.productoServicioService.guardarCatalogos(solicitudSincronizacion);
    }

    public boolean sincronizarParametros(Long idPuntoVenta) throws Exception {

        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));

        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = this.cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        SolicitudSincronizacion solicitud = this.obtenerSolicitud(puntoVenta.get(), cuis.get());

        this.parametroService.guardarParametros(solicitud);

        return true;
    }

    public RespuestaFechaHora sincronizarFechaHora(Long idPuntoVenta) throws RemoteException {
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        // Crear la solicitud con todos los datos
        SolicitudSincronizacion solicitud = new SolicitudSincronizacion();
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitud.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        solicitud.setCuis(cuis.get().getCodigo());
        solicitud.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());

        // Llamar al servicio SOAP real
        return fechaHoraService.obtenerFechaHora(solicitud);
    }
    public RespuestaListaParametricas sincronizarMensajesServicios(Long idPuntoVenta) throws RemoteException {
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        // Crear la solicitud con todos los datos
        SolicitudSincronizacion solicitud = new SolicitudSincronizacion();
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitud.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        solicitud.setCuis(cuis.get().getCodigo());
        solicitud.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());

        // Llamar al servicio SOAP real
        return mensajeServicioService.obtenerMensajesServicios(solicitud);
    }




    private SolicitudSincronizacion obtenerSolicitud(PuntoVentaEntity puntoVenta, CuisEntity cuis) {

        SolicitudSincronizacion solicitudSincronizacion = new SolicitudSincronizacion();
        solicitudSincronizacion.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudSincronizacion.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudSincronizacion.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudSincronizacion.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudSincronizacion.setCuis(cuis.getCodigo());
        solicitudSincronizacion.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        return solicitudSincronizacion;
    }
}
