package com.gaspar.facturador.application.rest.exception;

import com.gaspar.facturador.application.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author freddyar
 */
@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Error interno del servidor",
                List.of(e.getMessage())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProcessException.class)
    public final ResponseEntity<ExceptionResponse> handleAppException(ProcessException e) {
        ExceptionResponse exceptionResponse;
        if (e.getErrors() != null && !e.getErrors().isEmpty()) {
            exceptionResponse = new ExceptionResponse(e.getMessage(), e.getErrors());
        } else {
            exceptionResponse = new ExceptionResponse(e.getMessage(), List.of("No hay detalles adicionales"));
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RemoteException.class)
    public final ResponseEntity<ExceptionResponse> handleRemoteException(RemoteException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Error de comunicación con SIAT",
                List.of(e.getMessage(), "Verifique la conexión con los servidores de impuestos")
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_GATEWAY);
    }
}