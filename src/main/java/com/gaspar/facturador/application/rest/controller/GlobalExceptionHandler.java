package com.gaspar.facturador.application.rest.controller;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLGrammarException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleSQLGrammarException(SQLGrammarException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "SQL error: " + ex.getMessage());
        errorResponse.put("details", ex.getSQL());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}