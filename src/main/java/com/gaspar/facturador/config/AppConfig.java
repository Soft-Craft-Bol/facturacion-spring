package com.gaspar.facturador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${app.siat.token}")
    private String token;

    @Value("${app.siat.url}")
    private String siatUrl;

    @Value("${app.codigo-sistema}")
    private String codigoSistema;

    @Value("${app.codigo-modalidad}")
    private Integer codigoModalidad;

    @Value("${app.codigo-ambiente}")
    private Integer codigoAmbiente;

    @Value("${app.path.files}")
    private String pathFiles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSiatUrl() {
        return siatUrl;
    }

    public void setSiatUrl(String siatUrl) {
        this.siatUrl = siatUrl;
    }

    public String getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(String codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public Integer getCodigoModalidad() {
        return codigoModalidad;
    }

    public void setCodigoModalidad(Integer codigoModalidad) {
        this.codigoModalidad = codigoModalidad;
    }

    public Integer getCodigoAmbiente() {
        return codigoAmbiente;
    }

    public void setCodigoAmbiente(Integer codigoAmbiente) {
        this.codigoAmbiente = codigoAmbiente;
    }

    public String getPathFiles() {
        return pathFiles;
    }

    public void setPathFiles(String pathFiles) {
        this.pathFiles = pathFiles;
    }
}
