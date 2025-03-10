package com.gaspar.facturador.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GzipCompressor {

    /**
     * Comprime el contenido de la carpeta sourceDirPath en un archivo .tar.gz.
     * La carpeta principal (sourceDirPath) se incluirá en el archivo .tar.gz.
     */
    public static void compressDirectoryToTarGz(String sourceDirPath, String destTarGzPath) throws IOException {
        File sourceDir = new File(sourceDirPath);
        if (!sourceDir.exists()) {
            throw new IOException("La carpeta de origen no existe: " + sourceDirPath);
        }

        // Flujo base para escribir el archivo de salida .tar.gz
        try (FileOutputStream fos = new FileOutputStream(destTarGzPath);
             GzipCompressorOutputStream gzos = new GzipCompressorOutputStream(fos);
             TarArchiveOutputStream tarOs = new TarArchiveOutputStream(gzos)) {

            // Configuración para que no haya problemas con nombres largos
            tarOs.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);

            // Añadir la carpeta principal y su contenido al TAR
            addFilesToTar(tarOs, sourceDir, sourceDir.getName());
        }
    }

    /**
     * Función recursiva para añadir archivos/directorios al TAR.
     *
     * @param tarOs     OutputStream de TAR (ya encapsulado en GZIP).
     * @param file      Archivo o carpeta a añadir.
     * @param basePath  Ruta parcial dentro del TAR.
     */
    private static void addFilesToTar(TarArchiveOutputStream tarOs, File file, String basePath) throws IOException {
        // Construye nombre dentro del tar (path relativo)
        String entryName = basePath.isEmpty()
                ? file.getName()
                : basePath + "/" + file.getName();

        if (file.isFile()) {
            // Añadir archivo al TAR
            TarArchiveEntry entry = new TarArchiveEntry(file, entryName);
            entry.setSize(file.length());
            tarOs.putArchiveEntry(entry);

            // Copiamos bytes del archivo
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    tarOs.write(buffer, 0, read);
                }
            }
            tarOs.closeArchiveEntry();

        } else if (file.isDirectory()) {
            // Añadir directorio al TAR
            TarArchiveEntry dirEntry = new TarArchiveEntry(file, entryName + "/");
            tarOs.putArchiveEntry(dirEntry);
            tarOs.closeArchiveEntry();

            // Añadir hijos recursivamente
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFilesToTar(tarOs, child, entryName);
                }
            }
        }
    }
}