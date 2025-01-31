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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RecepcionMasivaService {

    private final AppConfig appConfig;
    private final ServicioFacturacion servicioFacturacion;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;

    public RecepcionMasivaService(
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

    public RespuestaRecepcion recibirFacturasMasivas(
            Long idPuntoVenta,
            byte[] archivo,
            String hashArchivo,
            int cantidadFacturas,
            String codigoEvento
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

        // Obtener el CUFD vigente
        Optional<CufdEntity> cufd = cufdRepository.findActual(puntoVenta.get());
        if (cufd.isEmpty()) {
            throw new ProcessException("CUFD vigente no encontrado");
        }

        // Obtener el CUIS vigente
        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) {
            throw new ProcessException("CUIS vigente no encontrado");
        }

        // Crear la solicitud de recepción masiva usando el constructor
        SolicitudRecepcionMasiva solicitudRecepcionMasiva = new SolicitudRecepcionMasiva(
                appConfig.getCodigoAmbiente(), // codigoAmbiente
                1, // codigoDocumentoSector (1 para compra/venta)
                2, // codigoEmision (2 para offline)
                appConfig.getCodigoModalidad(), // codigoModalidad
                puntoVenta.get().getCodigo(), // codigoPuntoVenta
                appConfig.getCodigoSistema(), // codigoSistema
                0, // codigoSucursal (0 para central)
                cufd.get().getCodigo(), // cufd
                cuis.get().getCodigo(), // cuis
                puntoVenta.get().getSucursal().getEmpresa().getNit(), // nit
                1, // tipoFacturaDocumento (1 para factura con derecho a crédito fiscal)
                archivo, // archivo
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), // fechaEnvio
                hashArchivo, // hashArchivo
                cantidadFacturas // cantidadFacturas
        );

        // Llamar al servicio de recepción masiva
        RespuestaRecepcion respuestaRecepcion = servicioFacturacion.recepcionMasivaFactura(solicitudRecepcionMasiva);

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