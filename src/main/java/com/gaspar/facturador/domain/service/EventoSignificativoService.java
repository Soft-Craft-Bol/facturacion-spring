package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.operaciones.*;
import com.gaspar.facturador.application.response.EventoSignificativoDTO;
import com.gaspar.facturador.application.response.EventoSignificativoRegistroResponse;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.*;
import com.gaspar.facturador.persistence.entity.*;
import com.gaspar.facturador.utils.DateUtil;
import jakarta.xml.bind.JAXBElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoSignificativoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventoSignificativoService.class);

    private final AppConfig appConfig;
    private final ServicioFacturacionOperaciones servicioOperaciones;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;
    private final IEventoSignificativoRepository eventoRepository;

    public EventoSignificativoService(
            AppConfig appConfig,
            ServicioFacturacionOperaciones servicioOperaciones,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository,
            ICuisRepository cuisRepository,
            IEventoSignificativoRepository eventoRepository) {
        this.appConfig = appConfig;
        this.servicioOperaciones = servicioOperaciones;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
        this.cuisRepository = cuisRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public EventoSignificativoRegistroResponse registrarEvento(
            Long idPuntoVenta,
            Integer codigoMotivo,
            String cufdEvento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) throws RemoteException {

        // 1. Validar conexión con SIAT
        verificarComunicacionSIAT();

        // 2. Obtener entidades necesarias
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta))
                .orElseThrow(() -> new ProcessException("Punto de venta no encontrado"));

        CufdEntity cufd = cufdRepository.findActual(puntoVenta)
                .orElseThrow(() -> new ProcessException("CUFD vigente no encontrado"));

        CuisEntity cuis = cuisRepository.findActual(puntoVenta)
                .orElseThrow(() -> new ProcessException("CUIS vigente no encontrado"));

        // 3. Registrar evento en SIAT
        RespuestaListaEventos respuesta = servicioOperaciones.registroEventoSignificativo(
                crearSolicitudEvento(puntoVenta, cuis, cufd, codigoMotivo, cufdEvento, fechaInicio, fechaFin));

        validarRespuestaSIAT(respuesta);

        // 4. Guardar evento localmente (72 horas)
        EventoSignificativoEntity evento = new EventoSignificativoEntity();
        evento.setPuntoVenta(puntoVenta);
        evento.setCodigoRecepcionEvento(respuesta.getCodigoRecepcionEventoSignificativo());
        evento.setCodigoMotivo(codigoMotivo);
        evento.setCufdEvento(cufdEvento);
        evento.setFechaInicio(fechaInicio);
        evento.setFechaFin(fechaFin);
        evento.setEtapa("REGISTRADO");
        evento.setFechaRegistro(LocalDateTime.now());
        evento.setVigente(true);

        eventoRepository.save(evento);

        return new EventoSignificativoRegistroResponse(evento.getId(), respuesta);
    }


    public void verificarComunicacionSIAT() throws RemoteException {
        RespuestaComunicacion respuesta = servicioOperaciones.verificarComunicacion();
        if (!Boolean.TRUE.equals(respuesta.isTransaccion())) {
            throw new ProcessException("Error de conexión con SIAT");
        }
    }

    private SolicitudEventoSignificativo crearSolicitudEvento(
            PuntoVentaEntity puntoVenta,
            CuisEntity cuis,
            CufdEntity cufd,
            Integer codigoMotivo,
            String cufdEvento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

        SolicitudEventoSignificativo solicitud = new SolicitudEventoSignificativo();
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        JAXBElement<Integer> codigoPuntoVentaElement = new JAXBElement<>(
                new QName("codigoPuntoVenta"),
                Integer.class,
                puntoVenta.getCodigo()
        );
        solicitud.setCodigoPuntoVenta(codigoPuntoVentaElement);
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setCodigoSucursal(0);
        solicitud.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitud.setCuis(cuis.getCodigo());
        solicitud.setCufd(cufd.getCodigo());
        solicitud.setCufdEvento(cufdEvento);
        solicitud.setCodigoMotivoEvento(codigoMotivo);
        solicitud.setDescripcion(obtenerDescripcionMotivo(codigoMotivo));
        solicitud.setFechaHoraInicioEvento(DateUtil.toXMLGregorianCalendar(fechaInicio));
        solicitud.setFechaHoraFinEvento(DateUtil.toXMLGregorianCalendar(fechaFin));

        return solicitud;
    }

    private void validarRespuestaSIAT(RespuestaListaEventos respuesta) {
        if (respuesta == null || respuesta.getCodigoRecepcionEventoSignificativo() == null) {
            throw new ProcessException("Respuesta inválida del SIAT");
        }
    }

    private String obtenerDescripcionMotivo(Integer codigoMotivo) {
        switch (codigoMotivo) {
            case 1: return "Corte del servicio de Internet";
            case 2: return "Inaccesibilidad al Servicio Web de la Administración Tributaria";
            case 3: return "Ingreso a zonas sin Internet por despliegue de puntos de venta";
            case 4: return "Venta en Lugares sin internet";
            case 5: return "Virus informático o falla de software";
            case 6: return "Cambio de infraestructura de sistema o falla de hardware";
            case 7: return "Corte de suministro de energía eléctrica";
            default: return "Evento significativo";
        }
    }

    public List<EventoSignificativoDTO> obtenerEventosVigentes(Integer idPuntoVenta) {
        PuntoVentaEntity puntoVenta = puntoVentaRepository.findById(idPuntoVenta)
                .orElseThrow(() -> new ProcessException("Punto de venta no encontrado"));

        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(72);

        return eventoRepository.findVigentesByPuntoVenta(puntoVenta, fechaLimite)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EventoSignificativoDTO convertirADTO(EventoSignificativoEntity evento) {
        EventoSignificativoDTO dto = new EventoSignificativoDTO();
        dto.setId(evento.getId());
        dto.setCodigoMotivo(evento.getCodigoMotivo());
        dto.setDescripcionMotivo(obtenerDescripcionMotivo(evento.getCodigoMotivo()));
        dto.setCufdEvento(evento.getCufdEvento());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setCodigoRecepcion(evento.getCodigoRecepcionEvento());
        dto.setFechaFin(evento.getFechaFin());
        return dto;
    }
}