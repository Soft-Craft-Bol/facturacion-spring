package com.gaspar.facturador.config;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.soap.MimeHeaders;
import java.net.MalformedURLException;
import java.net.URL;


@Configuration
public class ImpuestosClientConfig {

    @Value("${app.siat.token}")
    private String token;

    @Bean
    public bo.gob.impuestos.siat.ServicioFacturacionCodigos servicioFacturacionCodigos() throws AxisFault, MalformedURLException {

        bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigos service = new bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigosLocator() {

            @Override
            public Call createCall() {
                _call = new Call(this) {
                    @Override
                    public void setRequestMessage(Message msg)
                    {
                        super.setRequestMessage(msg);

                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }
        };
        return new bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigosSoapBindingStub(new URL(service.getServicioFacturacionCodigosPortAddress()), service);
    }

    @Bean
    public bo.gob.impuestos.siat.ServicioFacturacionSincronizacion servicioFacturacionSincronizacion() throws AxisFault, MalformedURLException {
        bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacion service = new bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionLocator() {
            @Override
            public Call createCall() {
                _call = new Call(this) {
                    @Override
                    public void setRequestMessage(Message msg) {
                        super.setRequestMessage(msg);

                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }
        };
        return new bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub(new URL(service.getServicioFacturacionSincronizacionPortAddress()), service);
    }

    @Bean
    public bo.gob.impuestos.siat.ServicioFacturacion servicioFacturacion() throws AxisFault, MalformedURLException {
        bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacion service = new bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionLocator() {
            @Override
            public Call createCall() {
                _call = new Call(this) {
                    @Override
                    public void setRequestMessage(Message msg) {
                        super.setRequestMessage(msg);

                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }
        };
        return new bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub(new URL(service.getServicioFacturacionPortAddress()), service);
    }
}
