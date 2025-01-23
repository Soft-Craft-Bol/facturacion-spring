package com.gaspar.facturador.domain.helpers;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


@Component
public class GZIPHelper {

    private final byte[] BUFFER = new byte[1024];

    public boolean compress(File archivo) {
        try (GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(archivo.getAbsolutePath() + ".zip"));
             FileInputStream in = new FileInputStream(archivo)) {
            int len;
            while ((len = in.read(BUFFER)) != -1) {
                out.write(BUFFER, 0, len);
            }
            out.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
