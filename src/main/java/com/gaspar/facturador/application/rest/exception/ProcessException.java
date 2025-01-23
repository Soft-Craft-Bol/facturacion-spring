package com.gaspar.facturador.application.rest.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author freddyar
 */
@Getter
@Setter
public class ProcessException extends RuntimeException {

    private List<String> errors;

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
