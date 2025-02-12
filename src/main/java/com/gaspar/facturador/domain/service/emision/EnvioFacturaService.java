package com.gaspar.facturador.domain.service.emision;

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
public class EnvioFacturaService {

    private final AppConfig appConfig;
    private final ServicioFacturacion clientSiatFacturacionCompraVenta;
    private final ICuisRepository cuisRepository;

    public EnvioFacturaService(AppConfig appConfig, ServicioFacturacion clientSiatFacturacionCompraVenta, ICuisRepository cuisRepository) {
        this.appConfig = appConfig;
        this.clientSiatFacturacionCompraVenta = clientSiatFacturacionCompraVenta;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaRecepcion enviar(
        PuntoVentaEntity puntoVenta,
        CufdEntity cufd,
        byte[] comprimidoByte
    ) throws RemoteException {

        RespuestaComunicacion respuestaComunicacion = clientSiatFacturacionCompraVenta.verificarComunicacion();
        if (!respuestaComunicacion.isTransaccion()) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta);
        if (cuis.isEmpty()) throw new ProcessException("Cuis vigente no encontrado.");

        String sha2 = Utils.obtenerSHA2(comprimidoByte);

        SolicitudRecepcionFactura solicitudRecepcionFactura = new SolicitudRecepcionFactura();
        solicitudRecepcionFactura.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudRecepcionFactura.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudRecepcionFactura.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudRecepcionFactura.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudRecepcionFactura.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitudRecepcionFactura.setCodigoDocumentoSector(CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue());
        solicitudRecepcionFactura.setCodigoEmision(CodigoTipoEmisionEnum.ONLINE.getValue());
        solicitudRecepcionFactura.setCodigoModalidad(CodigoModalidadEmisionEnum.ELECTRONICA_EN_LINEA.getValue());
        solicitudRecepcionFactura.setCufd(cufd.getCodigo());
        solicitudRecepcionFactura.setCuis(cuis.get().getCodigo());
        solicitudRecepcionFactura.setTipoFacturaDocumento(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        solicitudRecepcionFactura.setArchivo(comprimidoByte);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        solicitudRecepcionFactura.setFechaEnvio((LocalDateTime.now()).format(formatter));
        solicitudRecepcionFactura.setHashArchivo(sha2);

        RespuestaRecepcion respuestaRecepcion = this.clientSiatFacturacionCompraVenta.recepcionFactura(solicitudRecepcionFactura);

        if (respuestaRecepcion != null && respuestaRecepcion.getCodigoEstado() != 908) {
            String mensajes = "";
            for (MensajeRecepcion mensajeRecepcion : respuestaRecepcion.getMensajesList()) {
                mensajes += mensajeRecepcion.getDescripcion() + ". ";
            }
            throw new ProcessException(mensajes);
        }

        return respuestaRecepcion;
    }
}
