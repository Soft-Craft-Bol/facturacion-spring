package com.gaspar.facturador.application.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CierreCajaException extends RuntimeException {
    public CierreCajaException(String message) {
        super(message);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CierreCajaException.class)
    public final ResponseEntity<ExceptionResponse> handleCierreCajaException(CierreCajaException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Error en cierre de caja",
                List.of(e.getMessage() != null ? e.getMessage() : "Error inesperado")
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}