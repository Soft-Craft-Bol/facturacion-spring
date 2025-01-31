package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICufdRepository;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.Optional;

import static org.hibernate.bytecode.enhance.spi.interceptor.BytecodeInterceptorLogging.LOGGER;

@Service
public class AnulacionFacturaService {

    private final AppConfig appConfig;
    private final ServicioFacturacion servicioFacturacion;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;

    public AnulacionFacturaService(
            AppConfig appConfig,
            ServicioFacturacion servicioFacturacion,
            IPuntoVentaRepository puntoVentaRepository,
            ICufdRepository cufdRepository,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacion = servicioFacturacion;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaRecepcion anularFactura(
            Long idPuntoVenta,
            String cuf,
            String codigoMotivo
    ) throws RemoteException {
        // Verificar la comunicación con el SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacion.verificarComunicacion();
        if (!respuestaComunicacion.getTransaccion()) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        // Obtener el punto de venta
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) {
            throw new ProcessException("Punto venta no encontrado");
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

        // Crear la solicitud de anulación
        SolicitudAnulacion solicitudAnulacion = new SolicitudAnulacion();
        solicitudAnulacion.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudAnulacion.setCodigoEmision(1); // Código de emisión (1 para online)
        solicitudAnulacion.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudAnulacion.setCodigoSucursal(0); // Código de sucursal (0 para central)
        solicitudAnulacion.setCodigoMotivo(Integer.parseInt(codigoMotivo));
        solicitudAnulacion.setCodigoModalidad(appConfig.getCodigoModalidad()); // Modalidad de facturación (1 para electrónica en línea)
        solicitudAnulacion.setCuis(cuis.get().getCodigo());
        solicitudAnulacion.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitudAnulacion.setCuf(cuf); // CUF de la factura a anular
        solicitudAnulacion.setTipoFacturaDocumento(1); // Tipo de factura (1 para factura con derecho a crédito fiscal)
        solicitudAnulacion.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudAnulacion.setCodigoDocumentoSector(1); // Código de documento sector (1 para compra/venta)
        solicitudAnulacion.setCufd(cufd.get().getCodigo()); // Asignar el CUFD aquí

        // Llamar al servicio de anulación
        LOGGER.info("Solicitud de anulación: {}");
        RespuestaRecepcion respuestaRecepcion = servicioFacturacion.anulacionFactura(solicitudAnulacion);
        LOGGER.info("Respuesta del servicio: {}", respuestaRecepcion.getMensajesList());

        // Verificar la respuesta
        if (respuestaRecepcion != null && respuestaRecepcion.getCodigoEstado() != 908) {
            StringBuilder mensajes = new StringBuilder();
            if (respuestaRecepcion.getMensajesList() != null) {
                for (MensajeRecepcion mensaje : respuestaRecepcion.getMensajesList()) {
                    mensajes.append(mensaje.getDescripcion()).append(". ");
                }
            } else {
                mensajes.append("No se recibieron mensajes de error.");
            }
            throw new ProcessException(mensajes.toString());
        }

        return respuestaRecepcion;
    }
}
