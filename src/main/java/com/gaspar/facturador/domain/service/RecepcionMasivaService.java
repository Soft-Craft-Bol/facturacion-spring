package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.commons.CodigoDocumentoSectorEnum;
import com.gaspar.facturador.commons.CodigoModalidadEmisionEnum;
import com.gaspar.facturador.commons.CodigoTipoDocumentoFiscalEnum;
import com.gaspar.facturador.commons.CodigoTipoEmisionEnum;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.helpers.Utils;
import com.gaspar.facturador.domain.repository.ICuisRepository;
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
    private final ServicioFacturacion clientSiatFacturacionCompraVenta;
    private final ICuisRepository cuisRepository;

    public RecepcionMasivaService(
            AppConfig appConfig,
            ServicioFacturacion clientSiatFacturacionCompraVenta,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.clientSiatFacturacionCompraVenta = clientSiatFacturacionCompraVenta;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaRecepcion enviarPaqueteFacturas(
            PuntoVentaEntity puntoVenta,
            CufdEntity cufd,
            byte[] archivoComprimido,
            int cantidadFacturas
    ) throws RemoteException {

        // Verificar comunicación con el SIN
        RespuestaComunicacion respuestaComunicacion = clientSiatFacturacionCompraVenta.verificarComunicacion();
        if (!respuestaComunicacion.isTransaccion()) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        // Obtener CUIS vigente
        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta);
        if (cuis.isEmpty()) {
            throw new ProcessException("Cuis vigente no encontrado.");
        }

        // Calcular el hash del archivo
        String hashArchivo = Utils.obtenerSHA2(archivoComprimido);

        // Crear la solicitud de recepción masiva
        SolicitudRecepcionMasiva solicitud = new SolicitudRecepcionMasiva();
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitud.setCodigoEmision(CodigoTipoEmisionEnum.OFFLINE.getValue()); // 2 para offline
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitud.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitud.setCodigoModalidad(CodigoModalidadEmisionEnum.ELECTRONICA_EN_LINEA.getValue());
        solicitud.setCodigoDocumentoSector(CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue());
        solicitud.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitud.setCuis(cuis.get().getCodigo());
        solicitud.setCufd(cufd.getCodigo());
        solicitud.setTipoFacturaDocumento(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        solicitud.setArchivo(archivoComprimido);
        solicitud.setHashArchivo(hashArchivo);
        solicitud.setCantidadFacturas(cantidadFacturas);

        // Formatear la fecha de envío
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        solicitud.setFechaEnvio(LocalDateTime.now().format(formatter));

        // Enviar la solicitud al SIN
        RespuestaRecepcion respuesta = clientSiatFacturacionCompraVenta.recepcionMasivaFactura(solicitud);

        // Validar la respuesta
        if (respuesta != null && respuesta.getCodigoEstado() != 908) {
            StringBuilder mensajes = new StringBuilder();
            for (MensajeRecepcion mensaje : respuesta.getMensajesList()) {
                mensajes.append(mensaje.getDescripcion()).append(". ");
            }
            throw new ProcessException(mensajes.toString());
        }

        return respuesta;
    }
}