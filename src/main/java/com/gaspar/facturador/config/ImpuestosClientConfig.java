package com.gaspar.facturador.config;

import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.api.facturacion.codigos.ServicioFacturacionCodigos_Service;
import bo.gob.impuestos.siat.api.facturacion.operaciones.ServicioFacturacionOperaciones;
import bo.gob.impuestos.siat.api.facturacion.operaciones.ServicioFacturacionOperaciones_Service;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ServicioFacturacionSincronizacion_Service;
import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.ServicioFacturacion;
import bo.gob.impuestos.siat.api.servicio.facturacion.compra.venta.ServicioFacturacion_Service;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ImpuestosClientConfig {

    @Value("${app.siat.token}")
    private String token;

    @Value("${app.siat.facturacion.codigos.url}")
    private String facturacionCodigosUrl;

    @Value("${app.siat.facturacion.sincronizacion.url}")
    private String facturacionSincronizacionUrl;

    @Value("${app.siat.facturacion.compra.venta.url}")
    private String facturacionCompraVentaUrl;

    @Value("${app.siat.facturacion.operaciones.url}")
    private String facturacionOperacionesUrl;

    @Bean
    public ServicioFacturacionCodigos servicioFacturacionCodigos() throws MalformedURLException {
        URL url = new URL(facturacionCodigosUrl);
        ServicioFacturacionCodigos_Service service = new ServicioFacturacionCodigos_Service(url);

        ServicioFacturacionCodigos port = service.getServicioFacturacionCodigosPort();

        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("apiKey", Collections.singletonList("TokenApi " + token));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        return port;
    }

    @Bean
    public ServicioFacturacionSincronizacion servicioFacturacionSincronizacion() throws MalformedURLException {
        URL url = new URL(facturacionSincronizacionUrl);
        ServicioFacturacionSincronizacion_Service service = new ServicioFacturacionSincronizacion_Service(url);

        ServicioFacturacionSincronizacion port = service.getServicioFacturacionSincronizacionPort();

        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        // Configurar los encabezados HTTP correctamente
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("apiKey", Collections.singletonList("TokenApi " + token));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        return port;
    }

    //Facturacion
    @Bean
    public ServicioFacturacion servicioFacturacion() throws MalformedURLException {
        URL url = new URL(facturacionCompraVentaUrl);
        ServicioFacturacion_Service service = new ServicioFacturacion_Service(url);
        ServicioFacturacion port = service.getServicioFacturacionPort();

        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("apiKey", Collections.singletonList("TokenApi " + token));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        return port;
    }

    //Operaciones
    @Bean
    public ServicioFacturacionOperaciones servicioFacturacionOperaciones() throws MalformedURLException {
        URL url = new URL(facturacionOperacionesUrl);
        ServicioFacturacionOperaciones_Service service = new ServicioFacturacionOperaciones_Service(url);
        ServicioFacturacionOperaciones port = service.getServicioFacturacionOperacionesPort();

        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("apiKey", Collections.singletonList("TokenApi " + token));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        return port;
    }


}