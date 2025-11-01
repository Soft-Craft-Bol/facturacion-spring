package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.response.ArchivoFacturaResponse;
import com.gaspar.facturador.config.AppConfig;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExploradorArchivosService {

    private final AppConfig appConfig;

    public ExploradorArchivosService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public List<ArchivoFacturaResponse> explorarDirectorioFacturas() {
        String basePath = appConfig.getPathFiles() + "/facturas/";
        File directorioRaiz = new File(basePath);

        if (!directorioRaiz.exists()) {
            throw new RuntimeException("El directorio de facturas no existe");
        }

        return procesarDirectorio(directorioRaiz, basePath);
    }

    private List<ArchivoFacturaResponse> procesarDirectorio(File directorio, String basePath) {
        List<ArchivoFacturaResponse> respuesta = new ArrayList<>();
        File[] archivos = directorio.listFiles();

        if (archivos == null) {
            return respuesta;
        }

        for (File archivo : archivos) {
            ArchivoFacturaResponse item = new ArchivoFacturaResponse();
            item.setNombre(archivo.getName());
            item.setRuta(archivo.getAbsolutePath().replace(basePath, ""));
            item.setEsDirectorio(archivo.isDirectory());
            item.setTamanio(archivo.length());
            item.setFechaModificacion(LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochMilli(archivo.lastModified()),
                    ZoneId.systemDefault()
            ));

            // Extraer metadatos de la ruta
            extraerMetadatosDeRuta(item, archivo.getAbsolutePath());

            if (archivo.isDirectory()) {
                item.setArchivosHijos(procesarDirectorio(archivo, basePath));
            }

            respuesta.add(item);
        }

        return respuesta;
    }

    private void extraerMetadatosDeRuta(ArchivoFacturaResponse item, String rutaAbsoluta) {
        Path path = Paths.get(rutaAbsoluta);
        int count = path.getNameCount();

        // Determinar tipo de emisión
        if (rutaAbsoluta.contains("emision_normal")) {
            item.setTipoEmision("normal");
        } else if (rutaAbsoluta.contains("contingencia")) {
            item.setTipoEmision("contingencia");
        }

        // Extraer sucursal
        for (int i = 0; i < count; i++) {
            if (path.getName(i).toString().startsWith("sucursal_")) {
                item.setSucursal(path.getName(i).toString().replace("sucursal_", ""));
                break;
            }
        }

        if (item.getNombre().length() == 10 && item.isEsDirectorio()) {
            item.setCufd(item.getNombre());
        }

    }
}