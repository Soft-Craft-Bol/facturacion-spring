package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.helpers.Utils;
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
public class EnvioPaquetesService {

    private final AppConfig appConfig;
    private final ServicioFacturacion servicioFacturacion;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;
    private final ICuisRepository cuisRepository;

    public EnvioPaquetesService(
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

    public RespuestaRecepcion enviarPaqueteFacturas(
            Long idPuntoVenta,
            byte[] archivoComprimido,
            int cantidadFacturas
    ) throws RemoteException {
        // Verificar la comunicación con el SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacion.verificarComunicacion();
        if (!respuestaComunicacion.isTransaccion()) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        // Obtener el punto de venta
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(Math.toIntExact(idPuntoVenta));
        if (puntoVenta.isEmpty()) {
            throw new ProcessException("Punto de venta no encontrado");
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

        // Crear la solicitud de recepción masiva
        SolicitudRecepcionMasiva solicitudRecepcionMasiva = new SolicitudRecepcionMasiva();
        solicitudRecepcionMasiva.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudRecepcionMasiva.setCodigoPuntoVenta(puntoVenta.get().getCodigo());
        solicitudRecepcionMasiva.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudRecepcionMasiva.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        solicitudRecepcionMasiva.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitudRecepcionMasiva.setCuis(cuis.get().getCodigo());
        solicitudRecepcionMasiva.setCufd(cufd.get().getCodigo());
        solicitudRecepcionMasiva.setCodigoDocumentoSector(1); // Código de documento sector (1 para compra/venta)
        solicitudRecepcionMasiva.setCodigoEmision(2); // Código de emisión (2 para electrónica en línea)
        solicitudRecepcionMasiva.setCodigoModalidad(1); // Código de modalidad (1 para electrónica en línea)
        solicitudRecepcionMasiva.setTipoFacturaDocumento(1); // Tipo de factura (1 para factura con derecho a crédito fiscal)
        solicitudRecepcionMasiva.setArchivo(archivoComprimido);
        solicitudRecepcionMasiva.setCantidadFacturas(cantidadFacturas);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        solicitudRecepcionMasiva.setFechaEnvio(LocalDateTime.now().format(formatter));

        String hashArchivo = Utils.obtenerSHA2(archivoComprimido);
        solicitudRecepcionMasiva.setHashArchivo(hashArchivo);

        // Enviar el paquete de facturas
        RespuestaRecepcion respuestaRecepcion = servicioFacturacion.recepcionMasivaFactura(solicitudRecepcionMasiva);

        // Verificar la respuesta
        if (respuestaRecepcion != null && respuestaRecepcion.getCodigoEstado() != 908) {
            StringBuilder mensajeError = new StringBuilder("Error al enviar el paquete de facturas: ");
            for (MensajeRecepcion mensaje : respuestaRecepcion.getMensajesList()) {
                mensajeError.append(mensaje.getDescripcion()).append(". ");
            }
            throw new ProcessException(mensajeError.toString());
        }

        return respuestaRecepcion;
    }
}