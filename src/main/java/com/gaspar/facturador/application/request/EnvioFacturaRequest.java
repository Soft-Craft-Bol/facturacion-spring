package com.gaspar.facturador.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvioFacturaRequest {
    private String toEmail;
    private String clienteNombre;
    private String numeroFactura;
    private String cuf;
    private byte[] pdfContent;
}
