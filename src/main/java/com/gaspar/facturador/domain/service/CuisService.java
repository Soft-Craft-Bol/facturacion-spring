package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.codigos.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaCuis;
import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.codigos.SolicitudCuis;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import jakarta.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.stream.Collectors;


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


    public RespuestaCuis obtenerCuis(Integer idPuntoVenta) throws RemoteException {
        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta);
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuisExistente = this.cuisRepository.findActual(puntoVenta.get());

        if (cuisExistente.isPresent()) {
            RespuestaCuis respuestaExistente = new RespuestaCuis();
            respuestaExistente.setCodigo(cuisExistente.get().getCodigo());
            respuestaExistente.setFechaVigencia(convertToXMLGregorianCalendar(cuisExistente.get().getFechaVigencia()));
            respuestaExistente.setTransaccion(true);

            MensajeServicio mensaje = new MensajeServicio();
            mensaje.setDescripcion("CUIS existente recuperado de la base de datos local");
            respuestaExistente.getMensajesList().add(mensaje);

            return respuestaExistente;
        }

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

        if (respuestaCuis.getCodigo() != null && respuestaCuis.getFechaVigencia() != null) {
            this.cuisRepository.save(respuestaCuis, puntoVenta.get());
        }

        if (!respuestaCuis.isTransaccion() && respuestaCuis.getMensajesList() != null) {
            String mensajes = respuestaCuis.getMensajesList().stream()
                    .map(MensajeServicio::getDescripcion)
                    .collect(Collectors.joining(". "));
            throw new ProcessException(mensajes);
        }

        return respuestaCuis;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        try {
            GregorianCalendar gCalendar = GregorianCalendar.from(
                    localDateTime.atZone(ZoneId.systemDefault())
            );
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (Exception e) {
            throw new ProcessException("Error al convertir fecha: " + e.getMessage());
        }
    }

}
