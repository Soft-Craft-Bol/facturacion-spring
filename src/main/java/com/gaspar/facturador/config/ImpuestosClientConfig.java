package com.gaspar.facturador.config;

import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.ServicioFacturacion;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.headers.Header;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;

@Configuration
public class ImpuestosClientConfig {

    @Value("${app.siat.token}") // Token de autenticación desde application.properties
    private String token;

    // Interceptor para agregar la cabecera personalizada (apiKey)
    private Interceptor<SoapMessage> createHeaderInterceptor() {
        return new Interceptor<SoapMessage>() {
            @Override
            public void handleMessage(SoapMessage message) {
                // Crear la cabecera SOAP
                QName qname = new QName("apiKey");
                Header header = new Header(qname, "TokenApi " + token);
                message.getHeaders().add(header);
            }

            @Override
            public void handleFault(SoapMessage message) {
                // Manejo de errores (opcional)
            }
        };
    }

    // Configuración común para los clientes SOAP
    private <T> T createSoapClient(String serviceUrl, Class<T> serviceClass) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(serviceClass);
        factory.setAddress(serviceUrl);

        // Crear el cliente SOAP
        T client = (T) factory.create();

        // Agregar el interceptor para la cabecera personalizada
        Client cxfClient = org.apache.cxf.frontend.ClientProxy.getClient(client);
        cxfClient.getOutInterceptors().add(createHeaderInterceptor());

        return client;
    }

    // Bean para el servicio de códigos
    @Bean
    public ServicioFacturacionCodigos servicioFacturacionCodigos() {
        String serviceUrl = "https://pilotosiatservicios.impuestos.gob.bo/v2/FacturacionCodigos?wsdl";
        return createSoapClient(serviceUrl, ServicioFacturacionCodigos.class);
    }

    // Bean para el servicio de compra/venta
    @Bean
    public ServicioFacturacion servicioFacturacion() {
        String serviceUrl = "https://pilotosiatservicios.impuestos.gob.bo/v2/ServicioFacturacionCompraVenta?wsdl";
        return createSoapClient(serviceUrl, ServicioFacturacion.class);
    }

    // Bean para el servicio de sincronización
    @Bean
    public ServicioFacturacionSincronizacion servicioFacturacionSincronizacion() {
        String serviceUrl = "https://pilotosiatservicios.impuestos.gob.bo/v2/FacturacionSincronizacion?wsdl";
        return createSoapClient(serviceUrl, ServicioFacturacionSincronizacion.class);
    }
}