package com.gaspar.facturador.utils;

import java.io.IOException;

public class MainComprimir {
    public static void main(String[] args) {
        // Ruta de tu carpeta a comprimir
        String carpetaOrigen = "D:\\CursosDeSpring\\facturador\\facturas\\paquetes";
        // Ruta completa del archivo de salida
        String archivoComprimido = "D:\\CursosDeSpring\\facturador\\facturas\\paquetes.tar.gz";

        try {
            GzipCompressor.compressDirectoryToTarGz(carpetaOrigen, archivoComprimido);
            System.out.println("Compresión completada con éxito en: " + archivoComprimido);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //comentario
    }
}