package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.FacturaRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.FacturaEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReversionFacturaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReversionFacturaService.class);

    private final AppConfig appConfig;
    private final ServicioFacturacion servicioFacturacion;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;
    private final FacturaRepository facturaRepository;

    public ReversionFacturaService(
            AppConfig appConfig,
            ServicioFacturacion servicioFacturacion,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository,
            ICuisRepository cuisRepository, FacturaRepository facturaRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacion = servicioFacturacion;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
        this.cuisRepository = cuisRepository;
        this.facturaRepository = facturaRepository;
    }

    public RespuestaRecepcion reversionAnulacionFactura(
            Long idPuntoVenta,
            String cuf
    ) throws RemoteException {
        // Verificar la comunicación con el SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacion.verificarComunicacion();
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

        // Crear la solicitud de reversión de anulación
        SolicitudReversionAnulacion solicitudReversionAnulacion = new SolicitudReversionAnulacion();
        solicitudReversionAnulacion.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudReversionAnulacion.setCodigoEmision(1); // Código de emisión (1 para online)
        solicitudReversionAnulacion.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudReversionAnulacion.setCodigoSucursal(0); // Código de sucursal (0 para central)
        solicitudReversionAnulacion.setCodigoModalidad(appConfig.getCodigoModalidad()); // Modalidad de facturación (1 para electrónica en línea)
        solicitudReversionAnulacion.setCuis(cuis.get().getCodigo());
        solicitudReversionAnulacion.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitudReversionAnulacion.setCuf(cuf); // CUF de la factura a revertir
        solicitudReversionAnulacion.setTipoFacturaDocumento(1); // Tipo de factura (1 para factura con derecho a crédito fiscal)
        solicitudReversionAnulacion.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudReversionAnulacion.setCodigoDocumentoSector(1); // Código de documento sector (1 para compra/venta)
        solicitudReversionAnulacion.setCufd(cufd.get().getCodigo()); // Asignar el CUFD aquí

        // Llamar al servicio de reversión de anulación
        LOGGER.info("Solicitud de reversión de anulación: {}", solicitudReversionAnulacion);
        RespuestaRecepcion respuestaRecepcion = servicioFacturacion.reversionAnulacionFactura(solicitudReversionAnulacion);
        LOGGER.info("Respuesta del servicio: {}", obtenerMensajeServicio(respuestaRecepcion.getMensajesList()));

        // Verificar la respuesta
        if (respuestaRecepcion != null && respuestaRecepcion.getCodigoEstado() != 907) {
            throw new ProcessException(obtenerMensajeServicio(respuestaRecepcion.getMensajesList()));
        }

        Optional<FacturaEntity> facturaOpt = facturaRepository.findByCuf(cuf);
        if (facturaOpt.isPresent()) {
            facturaRepository.updateEstado(facturaOpt.get().getId(), "REVERTIDA");
        } else {
            throw new ProcessException("Factura no encontrada con el CUF proporcionado.");
        }

        return respuestaRecepcion;
    }

    private String obtenerMensajeServicio(List<MensajeRecepcion> mensajeServicioList) {
        if (mensajeServicioList == null || mensajeServicioList.isEmpty()) {
            return "No se recibieron mensajes de error.";
        }

        return mensajeServicioList.stream()
                .map(MensajeRecepcion::getDescripcion)
                .collect(Collectors.joining(". ")) + ".";
    }
}