package com.gaspar.facturador.utils;

import com.gaspar.facturador.config.AppConfig;
import org.apache.commons.compress.archivers.tar.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

@Component
public class FacturaCompressor {

    private final AppConfig appConfig;

    private int cantidadArchivosXML = 0;

    public FacturaCompressor(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public byte[] comprimirPaqueteFacturas() throws IOException {
        String facturasDirPath = appConfig.getPathFiles() + "/facturas/paquetes";
        String outputFilePath = facturasDirPath + "/facturas_paquete.tar.gz";

        File facturasDir = new File(facturasDirPath);
        if (!facturasDir.exists() || !facturasDir.isDirectory()) {
            throw new IOException("El directorio de facturas no existe: " + facturasDirPath);
        }

        cantidadArchivosXML = contarArchivosXML(facturasDir);

        if (cantidadArchivosXML == 0) {
            throw new IOException("No se encontraron archivos XML para comprimir");
        }

        File tarFile = new File(facturasDir, "facturas_paquete.tar");
        File gzipFile = new File(outputFilePath);

        // Crear TAR
        try (FileOutputStream fos = new FileOutputStream(tarFile);
             TarArchiveOutputStream tarOut = new TarArchiveOutputStream(fos)) {
            for (File file : facturasDir.listFiles((dir, name) -> name.endsWith(".xml"))) {
                addToTar(tarOut, file);
            }
        }

        // Comprimir TAR en GZIP
        try (FileInputStream fis = new FileInputStream(tarFile);
             FileOutputStream fos = new FileOutputStream(gzipFile);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOut.write(buffer, 0, bytesRead);
            }
        }

        byte[] compressedData = Files.readAllBytes(gzipFile.toPath());

        tarFile.delete();
        return compressedData;
    }

    private int contarArchivosXML(File directorio) {
        File[] archivosXML = directorio.listFiles((dir, name) -> name.endsWith(".xml"));
        return archivosXML != null ? archivosXML.length : 0;
    }

    private void addToTar(TarArchiveOutputStream tarOut, File file) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(file, file.getName());
        entry.setSize(file.length());
        tarOut.putArchiveEntry(entry);
        Files.copy(file.toPath(), tarOut);
        tarOut.closeArchiveEntry();
    }

    public int getCantidadArchivosXML() {
        return cantidadArchivosXML;
    }
}
