package com.gaspar.facturador.domain;

import com.gaspar.facturador.config.AppConfig;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Component
public class PaqueteFacturas {

    private final AppConfig appConfig;

    public PaqueteFacturas(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public byte[] crearPaqueteDeFacturas(List<byte[]> facturas, String directoryPath) throws IOException {
        if (facturas == null || facturas.isEmpty()) {
            throw new IllegalArgumentException("La lista de facturas no puede estar vacía");
        }

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        File gzipFile = new File(directory, "paquete_" + timestamp + ".tar.gz");

        try (FileOutputStream gzipOutput = new FileOutputStream(gzipFile);
             GZIPOutputStream gzipStream = new GZIPOutputStream(gzipOutput);
             TarArchiveOutputStream tarOutput = new TarArchiveOutputStream(gzipStream)) {

            tarOutput.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            tarOutput.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_POSIX);

            for (int i = 0; i < facturas.size(); i++) {
                byte[] facturaBytes = facturas.get(i);
                if (facturaBytes == null || facturaBytes.length == 0) {
                    System.err.println("Factura " + i + " está vacía. Se omitirá.");
                    continue;
                }

                // Verificar el contenido de los bytes antes de validar
                System.out.println("Contenido de la factura " + i + ": " + new String(facturaBytes, StandardCharsets.UTF_8));

                if (!esXmlValido(facturaBytes)) {
                    System.err.println("La factura " + i + " no es un XML válido. Se omitirá.");
                    continue;
                }

                String xmlFileName = String.format("factura_%s_%03d.xml", timestamp, i);
                TarArchiveEntry entry = new TarArchiveEntry(xmlFileName);
                entry.setSize(facturaBytes.length);
                tarOutput.putArchiveEntry(entry);
                tarOutput.write(facturaBytes);
                tarOutput.closeArchiveEntry();
            }

            tarOutput.finish(); // Importante para evitar corrupción
        } catch (IOException e) {
            System.err.println("Error al crear el paquete de facturas: " + e.getMessage());
            throw e;
        }

        return Files.readAllBytes(gzipFile.toPath());
    }

    private boolean esXmlValido(byte[] xmlBytes) {
        try {
            String xmlContent = new String(xmlBytes, StandardCharsets.UTF_8);
            if (!xmlContent.trim().startsWith("<?xml")) {
                System.err.println("El contenido no es un XML válido.");
                return false;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new ByteArrayInputStream(xmlBytes));
            return true;
        } catch (Exception e) {
            System.err.println("Error al validar XML: " + e.getMessage());
            return false;
        }
    }
}