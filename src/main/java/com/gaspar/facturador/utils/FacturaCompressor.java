package com.gaspar.facturador.utils;

import com.gaspar.facturador.config.AppConfig;
import com.gaspar.facturador.persistence.entity.PuntoVentaEntity;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

@Component
public class FacturaCompressor {

    private final AppConfig appConfig;
    private int cantidadArchivosXML = 0;

    public FacturaCompressor(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public byte[] comprimirPaquetePorCodigoControl(String codigoControl, PuntoVentaEntity puntoVenta, boolean esContingencia) throws IOException {
        // Determinar la ruta base según el tipo de emisión
        String tipoEmision = esContingencia ? "contingencia" : "emision_normal";
        String sucursalDirName = "sucursal_" + puntoVenta.getNombre().replace(" ", "_").toLowerCase();

        String facturasDirPath = appConfig.getPathFiles() + "/facturas/" + tipoEmision + "/" + sucursalDirName + "/" + codigoControl;
        File cufdDir = new File(facturasDirPath);

        if (!cufdDir.exists() || !cufdDir.isDirectory()) {
            throw new IOException("El directorio de facturas no existe: " + facturasDirPath);
        }

        // Contar archivos XML
        cantidadArchivosXML = contarArchivosXML(cufdDir);

        if (cantidadArchivosXML == 0) {
            throw new IOException("No se encontraron archivos XML para comprimir en el directorio: " + codigoControl);
        }

        // Crear directorio para paquetes si no existe
        String paquetesDirPath = appConfig.getPathFiles() + "/facturas/paquetes/";
        Files.createDirectories(Paths.get(paquetesDirPath));

        String outputFilePath = paquetesDirPath + "paquete_" + codigoControl + ".tar.gz";

        // Crear archivo TAR.GZ
        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(fos);
             TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzipOut)) {

            tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);

            // Agregar todos los archivos XML al TAR
            for (File file : cufdDir.listFiles((dir, name) -> name.endsWith(".xml"))) {
                addToTar(tarOut, file, file.getName());
            }
        }

        // Leer el archivo comprimido como byte array
        return Files.readAllBytes(Paths.get(outputFilePath));
    }

    private int contarArchivosXML(File directorio) {
        File[] archivosXML = directorio.listFiles((dir, name) -> name.endsWith(".xml"));
        return archivosXML != null ? archivosXML.length : 0;
    }

    private void addToTar(TarArchiveOutputStream tarOut, File file, String entryName) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(file, entryName);
        entry.setSize(file.length());
        tarOut.putArchiveEntry(entry);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                tarOut.write(buffer, 0, length);
            }
        }

        tarOut.closeArchiveEntry();
    }

    public int getCantidadArchivosXML() {return cantidadArchivosXML;}

    public boolean eliminarArchivosPorCodigoControl(String codigoControl, PuntoVentaEntity puntoVenta, boolean esContingencia) {
        try {
            String tipoEmision = esContingencia ? "contingencia" : "emision_normal";
            String sucursalDirName = "sucursal_" + puntoVenta.getNombre().replace(" ", "_").toLowerCase();

            String directorioPath = appConfig.getPathFiles() + "/facturas/" + tipoEmision + "/" + sucursalDirName + "/" + codigoControl;
            File directorio = new File(directorioPath);

            if (directorio.exists() && directorio.isDirectory()) {
                // Eliminar todos los archivos XML
                for (File file : directorio.listFiles((dir, name) -> name.endsWith(".xml"))) {
                    if (!file.delete()) {
                        System.err.println("No se pudo eliminar el archivo: " + file.getName());
                    }
                }

                // Intentar eliminar el directorio (solo si está vacío)
                return directorio.delete();
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar archivos por código de control: " + e.getMessage());
            return false;
        }
    }
}