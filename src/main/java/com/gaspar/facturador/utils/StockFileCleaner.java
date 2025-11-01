package com.gaspar.facturador.utils;

import com.gaspar.facturador.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class StockFileCleaner {

    private final AppConfig appConfig;

    // Ejecutar todos los días a las 2 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void limpiarArchivosAntiguos() {
        File carpeta = new File(appConfig.getPathFiles() + "/stock_inicial");
        if (!carpeta.exists()) return;

        long tresMesesMillis = 90L * 24 * 60 * 60 * 1000; // 90 días

        for (File archivo : carpeta.listFiles()) {
            if (archivo.isFile()) {
                long edad = new Date().getTime() - archivo.lastModified();
                if (edad > tresMesesMillis) {
                    archivo.delete();
                    System.out.println("Archivo eliminado: " + archivo.getName());
                }
            }
        }
    }
}
