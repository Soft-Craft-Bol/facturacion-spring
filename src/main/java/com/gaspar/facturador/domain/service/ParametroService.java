package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.*;
import com.gaspar.facturador.commons.ParametricaEnum;
import com.gaspar.facturador.domain.repository.IParametroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

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

    private void obtenerParametros(SolicitudSincronizacion solicitudSincronizacion, ParametricaEnum parametro) throws RemoteException {
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

        if (Boolean.FALSE.equals(respuestaParametros.isTransaccion())) {
            String mensajeError = obtenerMensajeServicio(respuestaParametros.getMensajesList());
            LOGGER.error("Error en la sincronización de parámetros ({}): {}", parametro, mensajeError);
        }

        List<ParametricasDto> listaCodigos = respuestaParametros.getListaCodigos();
        if (listaCodigos != null) {
            for (ParametricasDto parametricasDto : listaCodigos) {
                parametroRepository.save(parametricasDto, parametro);
            }
        }
    }

    private String obtenerMensajeServicio(List<MensajeServicio> mensajeServicioList) {
        if (mensajeServicioList == null || mensajeServicioList.isEmpty()) {
            return "No se recibieron mensajes de error.";
        }

        return mensajeServicioList.stream()
                .map(MensajeServicio::getDescripcion)
                .collect(Collectors.joining(". "));
    }
}
