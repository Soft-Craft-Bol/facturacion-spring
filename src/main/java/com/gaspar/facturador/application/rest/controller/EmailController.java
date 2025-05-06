package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.application.request.EnvioFacturaRequest;
import com.gaspar.facturador.domain.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar-factura")
    public ResponseEntity<String> enviarFactura(@RequestBody EnvioFacturaRequest request) {
        try {
            emailService.enviarFacturaElectronicaConPDF(
                    request.getToEmail(),
                    request.getClienteNombre(),
                    request.getNumeroFactura(),
                    request.getCuf(),
                    request.getPdfContent()
            );
            return ResponseEntity.ok("Factura enviada correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al enviar la factura: " + e.getMessage());
        }
    }
}