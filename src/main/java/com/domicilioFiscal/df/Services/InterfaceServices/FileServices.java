package com.domicilioFiscal.df.Services.InterfaceServices;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServices {

    public void save(MultipartFile file) throws Exception;

    public Resource load(String name) throws Exception;

}
