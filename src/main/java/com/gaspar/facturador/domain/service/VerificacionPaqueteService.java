package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.commons.CodigoDocumentoSectorEnum;
import com.gaspar.facturador.commons.CodigoTipoDocumentoFiscalEnum;
import com.gaspar.facturador.commons.CodigoTipoEmisionEnum;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.ICuisRepository;
import com.gaspar.facturador.persistence.entity.CufdEntity;
import com.gaspar.facturador.persistence.entity.CuisEntity;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.Optional;

@Service
public class VerificacionPaqueteService {

    private final AppConfig appConfig;
    private final ServicioFacturacion servicioFacturacion;
    private final ICuisRepository cuisRepository;

    public VerificacionPaqueteService(
            AppConfig appConfig,
            ServicioFacturacion servicioFacturacion,
            ICuisRepository cuisRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacion = servicioFacturacion;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaRecepcion validarRecepcionPaquete(
            PuntoVentaEntity puntoVenta,
            CufdEntity cufd,
            String codigoRecepcion
    ) throws RemoteException {
        // 1. Verificar comunicación con SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacion.verificarComunicacion();
        if (!respuestaComunicacion.isTransaccion()) {
            throw new ProcessException("No se pudo establecer comunicación con los servidores del S.I.N.");
        }
        CuisEntity cuis = cuisRepository.findActual(puntoVenta)
                .orElseThrow(() -> new ProcessException("CUIS vigente no encontrado para el punto de venta"));

        SolicitudValidacionRecepcion solicitud = buildSolicitudValidacion(puntoVenta, cufd, cuis, codigoRecepcion);

        RespuestaRecepcion respuesta = servicioFacturacion.validacionRecepcionPaqueteFactura(solicitud);

        validateRespuesta(respuesta);

        return respuesta;
    }

    private SolicitudValidacionRecepcion buildSolicitudValidacion(
            PuntoVentaEntity puntoVenta,
            CufdEntity cufd,
            CuisEntity cuis,
            String codigoRecepcion
    ) {
        SolicitudValidacionRecepcion solicitud = new SolicitudValidacionRecepcion();

        // Configuración común
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitud.setCodigoDocumentoSector(CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue());
        solicitud.setCodigoEmision(CodigoTipoEmisionEnum.OFFLINE.getValue());
        solicitud.setCodigoModalidad(appConfig.getCodigoModalidad());
        solicitud.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitud.setCufd(cufd.getCodigo());
        solicitud.setCuis(cuis.getCodigo());
        solicitud.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitud.setTipoFacturaDocumento(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());

        // Configuración específica
        solicitud.setCodigoRecepcion(codigoRecepcion);

        return solicitud;
    }


    private void validateRespuesta(RespuestaRecepcion respuesta) {
        if (respuesta == null) {
            throw new ProcessException("La respuesta del servicio de validación es nula");
        }

        // Verificar códigos de estado exitosos (902 es éxito según documentación SIAT)
        if (respuesta.getCodigoEstado() != null && respuesta.getCodigoEstado() == 902) {
            return; // Validación exitosa
        }

        // Procesar mensajes de error
        StringBuilder errorMessage = new StringBuilder();

        if (respuesta.getMensajesList() != null && !respuesta.getMensajesList().isEmpty()) {
            respuesta.getMensajesList().forEach(mensaje ->
                    errorMessage.append(mensaje.getDescripcion()).append(" | ")
            );
        }

        // Agregar código de estado si existe
        if (respuesta.getCodigoEstado() != null) {
            errorMessage.append("Código de estado: ").append(respuesta.getCodigoEstado());
        }

        throw new ProcessException(errorMessage.toString().trim());
    }
}