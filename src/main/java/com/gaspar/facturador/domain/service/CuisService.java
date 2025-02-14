package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.codigos.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCuis;
import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.codigos.SolicitudCuis;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import jakarta.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.util.Optional;


@Service
public class CuisService {

    private final AppConfig appConfig;
    private final ServicioFacturacionCodigos servicioFacturacionCodigos;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICuisRepository cuisRepository;

    public CuisService(
            AppConfig appConfig,
            @Qualifier("servicioFacturacionCodigos") ServicioFacturacionCodigos servicioFacturacionCodigos,
            IPuntoVentaRepository puntoVentaRepository,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacionCodigos = servicioFacturacionCodigos;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cuisRepository = cuisRepository;
    }


    public void obtenerCuis(Integer idPuntoVenta) throws RemoteException {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta);
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        SolicitudCuis solicitudCuis = new SolicitudCuis();
        solicitudCuis.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudCuis.setCodigoModalidad(this.appConfig.getCodigoModalidad());
        solicitudCuis.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudCuis.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudCuis.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        JAXBElement<Integer> codigoPuntoVentaElement = new JAXBElement<>(
                new QName("codigoPuntoVenta"),
                Integer.class,
                puntoVenta.get().getCodigo()
        );
        solicitudCuis.setCodigoPuntoVenta(codigoPuntoVentaElement);

        RespuestaCuis respuestaCuis = this.servicioFacturacionCodigos.cuis(solicitudCuis);
        System.out.println(respuestaCuis);

        if(!respuestaCuis.isTransaccion() && respuestaCuis.getMensajesList() != null) {
            String mensajes = "";
            for (MensajeServicio mensajeServicio : respuestaCuis.getMensajesList()) {
                mensajes += mensajeServicio.getDescripcion() + ". ";
            }
            throw new ProcessException(mensajes);
        }

        this.cuisRepository.save(respuestaCuis, puntoVenta.get());
    }
}
