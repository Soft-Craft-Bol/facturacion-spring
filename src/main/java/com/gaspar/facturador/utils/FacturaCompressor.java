package com.gaspar.facturador.utils;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;
import org.apache.commons.compress.archivers.tar.*;

public class FacturaCompressor {
    private static final String FACTURAS_DIR = "D:/CursosDeSpring/facturador/facturas/paquetes";
    private static final String OUTPUT_FILE = "D:/CursosDeSpring/facturador/facturas/paquetes/facturas_paquete.tar.gz";

    // Variable para almacenar el conteo de archivos XML
    private static int cantidadArchivosXML = 0;

    public static byte[] comprimirPaqueteFacturas() throws IOException {
        File facturasDir = new File(FACTURAS_DIR);
        if (!facturasDir.exists() || !facturasDir.isDirectory()) {
            throw new IOException("El directorio de facturas no existe");
        }

        // Contar archivos XML antes de procesarlos
        cantidadArchivosXML = contarArchivosXML(facturasDir);

        // Si no hay archivos XML, lanzar excepción
        if (cantidadArchivosXML == 0) {
            throw new IOException("No se encontraron archivos XML para comprimir");
        }

        File tarFile = new File(FACTURAS_DIR, "facturas_paquete.tar");
        File gzipFile = new File(OUTPUT_FILE);

        // Crear el archivo TAR
        try (FileOutputStream fos = new FileOutputStream(tarFile);
             TarArchiveOutputStream tarOut = new TarArchiveOutputStream(fos)) {

            // Agregar archivos XML al TAR
            for (File file : facturasDir.listFiles((dir, name) -> name.endsWith(".xml"))) {
                addToTar(tarOut, file);
            }
        }

        // Comprimir el TAR a GZIP
        try (FileInputStream fis = new FileInputStream(tarFile);
             FileOutputStream fos = new FileOutputStream(gzipFile);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOut.write(buffer, 0, bytesRead);
            }
        }

        // Leer el archivo comprimido en un array de bytes
        byte[] compressedData = Files.readAllBytes(gzipFile.toPath());

        // Eliminar archivos temporales
        tarFile.delete();
        return compressedData;
    }

    // Método para contar archivos XML en el directorio
    private static int contarArchivosXML(File directorio) {
        File[] archivosXML = directorio.listFiles((dir, name) -> name.endsWith(".xml"));
        return archivosXML != null ? archivosXML.length : 0;
    }

    private static void addToTar(TarArchiveOutputStream tarOut, File file) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(file, file.getName());
        entry.setSize(file.length());
        tarOut.putArchiveEntry(entry);
        Files.copy(file.toPath(), tarOut);
        tarOut.closeArchiveEntry();
    }

    // Método para obtener la cantidad de archivos XML procesados
    public static int getCantidadArchivosXML() {
        return cantidadArchivosXML;
    }
}