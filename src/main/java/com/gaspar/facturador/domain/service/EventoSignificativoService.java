package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.operaciones.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import com.gaspar.facturador.utils.DateUtil;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EventoSignificativoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventoSignificativoService.class);

    private final AppConfig appConfig;
    private final ServicioFacturacionOperaciones servicioFacturacionOperaciones;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;

    public EventoSignificativoService(
            AppConfig appConfig,
            ServicioFacturacionOperaciones servicioFacturacionOperaciones,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacionOperaciones = servicioFacturacionOperaciones;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaListaEventos registrarEventoSignificativo(
            Long idPuntoVenta,
            String descripcion,
            Integer codigoMotivoEvento,
            String cufdEvento,
            LocalDateTime fechaHoraInicioEvento,
            LocalDateTime fechaHoraFinEvento
    ) throws RemoteException {
        // Verificar la comunicación con el SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacionOperaciones.verificarComunicacion();
        if (!Boolean.TRUE.equals(respuestaComunicacion.isTransaccion())) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        // Obtener el punto de venta
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) {
            throw new ProcessException("Punto de venta no encontrado");
        }

        // Obtener el CUFD vigente desde el repositorio
        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) {
            throw new ProcessException("CUFD vigente no encontrado");
        }

        // Obtener el CUIS vigente
        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) {
            throw new ProcessException("CUIS vigente no encontrado");
        }

        // Crear la solicitud de evento significativo
        SolicitudEventoSignificativo solicitudEventoSignificativo = new SolicitudEventoSignificativo();
        solicitudEventoSignificativo.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        JAXBElement<Integer> codigoPuntoVentaElement = new JAXBElement<>(
                new QName("codigoPuntoVenta"),
                Integer.class,
                puntoVenta.get().getCodigo()
        );
        solicitudEventoSignificativo.setCodigoPuntoVenta(codigoPuntoVentaElement);
        solicitudEventoSignificativo.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudEventoSignificativo.setCodigoSucursal(0); // Código de sucursal (0 para central)
        solicitudEventoSignificativo.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudEventoSignificativo.setCuis(cuis.get().getCodigo());
        solicitudEventoSignificativo.setCufd(cufd.get().getCodigo());
        solicitudEventoSignificativo.setCufdEvento(cufdEvento); // CUFD con el que se generó el evento
        solicitudEventoSignificativo.setCodigoMotivoEvento(codigoMotivoEvento);
        solicitudEventoSignificativo.setDescripcion(descripcion);

        // Convertir fechas a XMLGregorianCalendar
        solicitudEventoSignificativo.setFechaHoraInicioEvento(DateUtil.toXMLGregorianCalendar(fechaHoraInicioEvento));
        solicitudEventoSignificativo.setFechaHoraFinEvento(DateUtil.toXMLGregorianCalendar(fechaHoraFinEvento));

        // Llamar al servicio de registro de evento significativo
        LOGGER.info("Solicitud de registro de evento significativo: {}", solicitudEventoSignificativo);
        RespuestaListaEventos respuesta = servicioFacturacionOperaciones.registroEventoSignificativo(solicitudEventoSignificativo);
        LOGGER.info("Respuesta del servicio: {}", respuesta);

        // Verificar la respuesta
        if (respuesta != null && (respuesta.getCodigoRecepcionEventoSignificativo() == null || respuesta.getCodigoRecepcionEventoSignificativo() <= 0)) {
            StringBuilder mensajeError = new StringBuilder("Error al registrar el evento significativo: ");
            for (MensajeServicio mensaje : respuesta.getMensajesList()) {
                mensajeError.append(mensaje.getDescripcion()).append(". ");
            }
            throw new ProcessException(mensajeError.toString());
        }

        return respuesta;
    }

}