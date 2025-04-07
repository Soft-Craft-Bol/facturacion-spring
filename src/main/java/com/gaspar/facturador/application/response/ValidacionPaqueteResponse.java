package com.gaspar.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ValidacionPaqueteResponse {
    private String mensaje;
    private Integer codigoEstado;
    private String codigoRecepcion;
    private String estado;
    private List<String> mensajesDetallados; // Para múltiples mensajes de validación
    private LocalDateTime fechaProcesamiento;
    private String codigoTransaccion;

    // Método estático para respuestas exitosas
    public static ValidacionPaqueteResponse success(Integer codigoEstado, String codigoRecepcion) {
        ValidacionPaqueteResponse response = new ValidacionPaqueteResponse();
        response.setEstado("VALIDACION_EXITOSA");
        response.setMensaje("Validación completada satisfactoriamente");
        response.setCodigoEstado(codigoEstado);
        response.setCodigoRecepcion(codigoRecepcion);
        response.setFechaProcesamiento(LocalDateTime.now());
        return response;
    }

    // Método estático para respuestas fallidas
    public static ValidacionPaqueteResponse error(Integer codigoEstado, String mensaje, List<String> mensajesDetallados) {
        ValidacionPaqueteResponse response = new ValidacionPaqueteResponse();
        response.setEstado("ERROR_VALIDACION");
        response.setMensaje(mensaje);
        response.setCodigoEstado(codigoEstado);
        response.setMensajesDetallados(mensajesDetallados);
        response.setFechaProcesamiento(LocalDateTime.now());
        return response;
    }
}