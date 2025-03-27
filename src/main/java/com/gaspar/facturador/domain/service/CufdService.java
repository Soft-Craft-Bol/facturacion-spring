package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.codigos.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCufd;
import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.codigos.SolicitudCufd;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import jakarta.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;



@Service
public class CufdService {

    private final AppConfig appConfig;

    private final ServicioFacturacionCodigos servicioFacturacionCodigos;

    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICuisRepository cuisRepository;
    private final ICufdRepository cufdRepository;

    public CufdService(
        AppConfig appConfig,
        ServicioFacturacionCodigos servicioFacturacionCodigos,
        IPuntoVentaRepository puntoVentaRepository,
        ICuisRepository cuisRepository,
        ICufdRepository cufdRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacionCodigos = servicioFacturacionCodigos;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cuisRepository = cuisRepository;
        this.cufdRepository = cufdRepository;
    }

    public void obtenerCufd(Integer idPuntoVenta) throws RemoteException {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta);
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = this.cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        SolicitudCufd solicitudCufd = new SolicitudCufd();
        solicitudCufd.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudCufd.setCodigoModalidad(this.appConfig.getCodigoModalidad());
        solicitudCufd.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudCufd.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudCufd.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        JAXBElement<Integer> codigoPuntoVentaElement = new JAXBElement<>(
                new QName("codigoPuntoVenta"),
                Integer.class,
                puntoVenta.get().getCodigo()
        );
        solicitudCufd.setCodigoPuntoVenta(codigoPuntoVentaElement);
        //solicitudCufd.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitudCufd.setCuis(cuis.get().getCodigo());

        RespuestaCufd respuestaCufd = this.servicioFacturacionCodigos.cufd(solicitudCufd);

        if(!respuestaCufd.isTransaccion() && respuestaCufd.getMensajesList() != null) {
            String mensajes = "";
            for (MensajeServicio mensajeServicio : respuestaCufd.getMensajesList()) {
                mensajes += mensajeServicio.getDescripcion() + ". ";
            }
            throw new ProcessException(mensajes);
        }

        this.cufdRepository.save(respuestaCufd, puntoVenta.get());
    }

    public List<CufdEntity> obtenerCufdsAnteriores(Integer idPuntoVenta) {
        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta);
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        return this.cufdRepository.findAnteriores(puntoVenta.get());
    }
}
