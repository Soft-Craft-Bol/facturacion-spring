package com.gaspar.facturador.application.rest.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class ExceptionResponse {
    private String timestamp;
    private String message;
    private List<String> details;
    private String errorCode; // Podemos agregar códigos de error específicos

    public ExceptionResponse(String message) {
        this(message, List.of());
    }

    public ExceptionResponse(String message, List<String> details) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.message = message != null ? message : "Sin mensaje";
        this.details = details != null ? details : List.of();
    }

}