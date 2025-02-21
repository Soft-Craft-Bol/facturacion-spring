package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.*;
import com.gaspar.facturador.application.rest.exception.ProcessException;
import com.gaspar.facturador.commons.CodigoDocumentoSectorEnum;
import com.gaspar.facturador.commons.CodigoTipoDocumentoFiscalEnum;
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
            PuntoVentaEntity puntoVenta,
            CufdEntity cufd,
            byte[] comprimidoByte,
            long cantidadFacturas,
            long codigoEvento
    ) throws RemoteException {
        // Verificar la comunicación con el SIAT
        RespuestaComunicacion respuestaComunicacion = servicioFacturacion.verificarComunicacion();
        if (!respuestaComunicacion.isTransaccion()) {
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N.");
        }

        Optional<CuisEntity> cuis = cuisRepository.findActual(puntoVenta);
        if (cuis.isEmpty()) throw new ProcessException("Cuis vigente no encontrado.");

        // Verificar que el archivo no esté vacío
        if (comprimidoByte == null || comprimidoByte.length == 0) {
            throw new ProcessException("El archivo comprimido está vacío.");
        }

        // Calcular el hash del archivo
        String sha2 = Utils.obtenerSHA2(comprimidoByte);
        System.out.println("Hash generado: " + sha2);

        // Crear la solicitud de recepción masiva
        SolicitudRecepcionPaquete solicitudRecepcionPaquete = new SolicitudRecepcionPaquete();
        solicitudRecepcionPaquete.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudRecepcionPaquete.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudRecepcionPaquete.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudRecepcionPaquete.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudRecepcionPaquete.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitudRecepcionPaquete.setCuis(cuis.get().getCodigo());
        solicitudRecepcionPaquete.setCufd(cufd.getCodigo());
        solicitudRecepcionPaquete.setCodigoDocumentoSector(CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue());
        solicitudRecepcionPaquete.setCodigoEmision(2);  // Código de emisión en línea
        solicitudRecepcionPaquete.setCodigoModalidad(appConfig.getCodigoModalidad());
        solicitudRecepcionPaquete.setTipoFacturaDocumento(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        solicitudRecepcionPaquete.setCantidadFacturas((int) cantidadFacturas);
        solicitudRecepcionPaquete.setCodigoEvento(codigoEvento);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        solicitudRecepcionPaquete.setFechaEnvio(LocalDateTime.now().format(formatter));
        solicitudRecepcionPaquete.setHashArchivo(sha2);
        solicitudRecepcionPaquete.setArchivo(comprimidoByte);


        // Enviar el paquete de facturas
        RespuestaRecepcion respuestaRecepcion = servicioFacturacion.recepcionPaqueteFactura(solicitudRecepcionPaquete);

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