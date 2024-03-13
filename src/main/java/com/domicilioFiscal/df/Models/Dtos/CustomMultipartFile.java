package com.domicilioFiscal.df.Models.Dtos;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class CustomMultipartFile implements MultipartFile {
    private final String nombre;
    private final byte[] contenido;

    public CustomMultipartFile(String nombre, byte[] contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
    }

    @Override
    public String getName() {
        return nombre;
    }

    @Override
    public String getOriginalFilename() {
        return nombre;
    }

    @Override
    public String getContentType() {
        // Puedes proporcionar el tipo de contenido adecuado aquí si lo sabes
        return null;
    }

    @Override
    public boolean isEmpty() {
        return contenido == null || contenido.length == 0;
    }

    @Override
    public long getSize() {
        return contenido.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return contenido;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null; // Implementa si es necesario
    }

    @Override
    public void transferTo(java.io.File file) throws IOException, IllegalStateException {
        // Implementa si es necesario
    }
}