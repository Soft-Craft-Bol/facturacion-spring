package com.gaspar.facturador.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.url.base}")
    private String appBaseUrl;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void enviarFacturaElectronicaConPDF(String toEmail, String clienteNombre, String numeroFactura,
                                               String cuf, byte[] pdfContent)
            throws MessagingException {
        String subject = "Factura Electrónica " + numeroFactura;
        String htmlContent = buildFacturaEmailContent(clienteNombre, numeroFactura, cuf);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);

        // Adjuntar PDF
        helper.addAttachment(
                "FACTURA_"+numeroFactura+".pdf",
                new ByteArrayResource(pdfContent)
        );

        mailSender.send(message);
    }


    @Async
    public void enviarNotificacionAnulacion(String toEmail, String numeroFactura, String motivo) throws MessagingException {
        String subject = "Anulación de Factura " + numeroFactura;
        String htmlContent = buildAnulacionEmailContent(numeroFactura, motivo);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);

        mailSender.send(message);
    }

    private String buildFacturaEmailContent(String clienteNombre, String numeroFactura, String cuf) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Factura Electrónica</title>" +
                "<style>" +
                "  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }" +
                "  .container { max-width: 600px; margin: 20px auto; border: 1px solid #e1e1e1; border-radius: 8px; overflow: hidden; }" +
                "  .header { background-color: #FFAF5A; color: white; padding: 20px; text-align: center; }" +
                "  .content { padding: 25px; background-color: #ffffff; }" +
                "  .footer { padding: 15px; text-align: center; font-size: 12px; color: #777; background-color: #f9f9f9; }" +
                "  .button { display: inline-block; padding: 10px 20px; background-color: #FFAF5A; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; margin: 15px 0; }" +
                "  .details { margin: 20px 0; border-left: 4px solid #FFAF5A; padding-left: 15px; }" +
                "  .detail-item { margin-bottom: 10px; }" +
                "  .logo { max-width: 150px; margin-bottom: 15px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "  <div class=\"container\">" +
                "    <div class=\"header\">" +
                "      <h2>FACTURA ELECTRÓNICA</h2>" +
                "    </div>" +
                "    <div class=\"content\">" +
                "      <p>Estimado/a <strong>" + clienteNombre + "</strong>,</p>" +
                "      <p>Adjunto encontrará su factura electrónica con los siguientes detalles:</p>" +
                "      <div class=\"details\">" +
                "        <div class=\"detail-item\"><strong>Número de Factura:</strong> " + numeroFactura + "</div>" +
                "        <div class=\"detail-item\"><strong>Código Único de Factura (CUF):</strong> " + cuf + "</div>" +
                "      </div>" +
                "      <p>El archivo PDF adjunto contiene su comprobante fiscal.</p>" + // Mensaje actualizado
                "      <p>Puede consultar esta factura en nuestro sistema: <a href=\"" + appBaseUrl + "/facturas/" + cuf + "\">Ver factura en línea</a></p>" +
                "      <p>Para cualquier consulta, no dude en contactarnos.</p>" +
                "    </div>" +
                "    <div class=\"footer\">" +
                "      <p>Este es un mensaje automático, por favor no responda a este correo.</p>" +
                "      <p>&copy; " + java.time.Year.now().getValue() + " Inpased. Todos los derechos reservados.</p>" +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }

    private String buildAnulacionEmailContent(String numeroFactura, String motivo) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Anulación de Factura</title>" +
                "<style>" +
                "  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }" +
                "  .container { max-width: 600px; margin: 20px auto; border: 1px solid #e1e1e1; border-radius: 8px; overflow: hidden; }" +
                "  .header { background-color: #E53E3E; color: white; padding: 20px; text-align: center; }" +
                "  .content { padding: 25px; background-color: #ffffff; }" +
                "  .footer { padding: 15px; text-align: center; font-size: 12px; color: #777; background-color: #f9f9f9; }" +
                "  .details { margin: 20px 0; border-left: 4px solid #E53E3E; padding-left: 15px; }" +
                "  .detail-item { margin-bottom: 10px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "  <div class=\"container\">" +
                "    <div class=\"header\">" +
                "      <h2>ANULACIÓN DE FACTURA</h2>" +
                "    </div>" +
                "    <div class=\"content\">" +
                "      <p>Le informamos que su factura ha sido anulada:</p>" +
                "      <div class=\"details\">" +
                "        <div class=\"detail-item\"><strong>Número de Factura:</strong> " + numeroFactura + "</div>" +
                "        <div class=\"detail-item\"><strong>Motivo:</strong> " + motivo + "</div>" +
                "      </div>" +
                "      <p>Si considera que esto es un error, por favor contacte con nuestro departamento de facturación.</p>" +
                "    </div>" +
                "    <div class=\"footer\">" +
                "      <p>Este es un mensaje automático, por favor no responda a este correo.</p>" +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }
}