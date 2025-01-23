package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.gaspar.facturador.commons.ParametricaEnum;
import com.gaspar.facturador.domain.repository.IParametroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;


@Service
public class ParametroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametroService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IParametroRepository parametroRepository;

    public ParametroService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, IParametroRepository parametroRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.parametroRepository = parametroRepository;
    }

    public void guardarParametros(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        parametroRepository.deleteAll();
        for (ParametricaEnum parametro : ParametricaEnum.values()) {
            this.obtenerParametros(solicitudSincronizacion, parametro);
        }
    }

    private void obtenerParametros(
            SolicitudSincronizacion solicitudSincronizacion,
            ParametricaEnum parametro
    ) throws RemoteException {
        RespuestaListaParametricas respuestaParametros = null;

        switch (parametro) {
            case EVENTOS_SIGNIFICATIVOS:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaEventosSignificativos(solicitudSincronizacion);
                break;
            case MOTIVO_ANULACION:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaMotivoAnulacion(solicitudSincronizacion);
                break;
            case PAIS_ORIGEN:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaPaisOrigen(solicitudSincronizacion);
                break;
            case TIPO_DOCUMENTO_IDENTIDAD:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoDocumentoIdentidad(solicitudSincronizacion);
                break;
            case TIPO_DOCUMENTO_SECTOR:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoDocumentoSector(solicitudSincronizacion);
                break;
            case TIPO_EMISION:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoEmision(solicitudSincronizacion);
                break;
            case TIPO_HABITACION:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoHabitacion(solicitudSincronizacion);
                break;
            case TIPO_METODO_PAGO:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoMetodoPago(solicitudSincronizacion);
                break;
            case TIPO_MONEDA:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoMoneda(solicitudSincronizacion);
                break;
            case TIPO_PUNTO_VENTA:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTipoPuntoVenta(solicitudSincronizacion);
                break;
            case TIPOS_FACTURA:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaTiposFactura(solicitudSincronizacion);
                break;
            case UNIDAD_MEDIDA:
                respuestaParametros = servicioFacturacionSincronizacion.sincronizarParametricaUnidadMedida(solicitudSincronizacion);
                break;
        }

        if (!respuestaParametros.getTransaccion()) {
            LOGGER.error(this.obtenerMensajeServicio(respuestaParametros.getMensajesList()));
        }

        for (ParametricasDto parametricasDto : respuestaParametros.getListaCodigos()) {
            parametroRepository.save(parametricasDto, parametro);
        }
    }

    private String  obtenerMensajeServicio(MensajeServicio[] mensajeServicioList) {
        String mensaje = "";
        if (mensajeServicioList != null) {
            for (MensajeServicio mensajeServicio : mensajeServicioList) {
                mensaje += mensajeServicio.getDescripcion() + ". ";
            }
        }
        return mensaje;
    }
}
