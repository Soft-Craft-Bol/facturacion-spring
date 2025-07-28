package com.gaspar.facturador.domain.service;

import bo.gob.impuestos.siat.api.facturacion.codigos.MensajeServicio;
import bo.gob.impuestos.siat.api.facturacion.codigos.RespuestaVerificarNit;
import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.codigos.SolicitudVerificarNit;
import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.domain.repository.IPuntoVentaRepository;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VerificarNitService {

    private final AppConfig appConfig;
    private final ServicioFacturacionCodigos servicioFacturacionCodigos;
    private final IPuntoVentaRepository puntoVentaRepository;

    public VerificarNitService(
        AppConfig appConfig,
        ServicioFacturacionCodigos servicioFacturacionCodigos,
        IPuntoVentaRepository puntoVentaRepository
    ) {
        this.appConfig = appConfig;
        this.servicioFacturacionCodigos = servicioFacturacionCodigos;
        this.puntoVentaRepository = puntoVentaRepository;
    }

    public Map<String, Object> verificarNit(Long nit, Integer idPuntoVenta) {
        Map<String, Object> response = new HashMap<>();
        Optional<PuntoVentaEntity> puntoVenta = puntoVentaRepository.findById(idPuntoVenta);
        if (puntoVenta.isEmpty()) {
            throw new IllegalArgumentException("Punto de venta no encontrado");
        }

        SolicitudVerificarNit solicitud = new SolicitudVerificarNit();
        solicitud.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitud.setCodigoSistema(appConfig.getCodigoSistema());
        solicitud.setNit(puntoVenta.get().getSucursal().getEmpresa().getNit());
        solicitud.setCodigoSucursal(puntoVenta.get().getSucursal().getCodigo());
        solicitud.setCodigoModalidad(appConfig.getCodigoModalidad());
        solicitud.setNitParaVerificacion(nit);

        RespuestaVerificarNit respuesta = servicioFacturacionCodigos.verificarNit(solicitud);

        response.put("exito", respuesta.isTransaccion());
        response.put("codigoSucursal", puntoVenta.get().getSucursal().getCodigo());

        if (!respuesta.isTransaccion() && respuesta.getMensajesList() != null) {
            StringBuilder mensajes = new StringBuilder();
            for (MensajeServicio mensaje : respuesta.getMensajesList()) {
                mensajes.append(mensaje.getDescripcion()).append(". ");
            }
            response.put("mensaje", mensajes.toString());
        } else {
            response.put("mensaje", "Verificación exitosa");
        }

        return response;
    }
}
