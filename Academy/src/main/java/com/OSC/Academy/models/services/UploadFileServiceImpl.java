package com.OSC.Academy.models.services;

import com.OSC.Academy.controllers.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static String UPLOAD_DIRECTORY = "uploads";
    @Override
    public Resource upload(String nameImage) throws MalformedURLException {
        Path fileRoute = getPath(nameImage) ;
        log.info(fileRoute.toString());

        Resource  resource = new UrlResource(fileRoute.toUri());


        if(!resource.exists() && !resource.isReadable()){
            fileRoute = Paths.get("src/main/resources/static/images").resolve("NotUser.png").toAbsolutePath();

            resource = new UrlResource(fileRoute.toUri());

            log.error("Error no se pudo cargar la imagen: " + nameImage);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");

        Path fileRoute = getPath(fileName);
        log.info(fileRoute.toString());

        Files.copy(file.getInputStream(), fileRoute);

        return fileName;
    }

    @Override
    public boolean delete(String nameImage) {

        if(nameImage != null && nameImage.length() >0) {
            Path oldImageRoute = Paths.get("uploads").resolve(nameImage).toAbsolutePath();
            File oldImageFile = oldImageRoute.toFile();
            if(oldImageFile.exists() && oldImageFile.canRead()) {
                oldImageFile.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nameImage) {
        return Paths.get(UPLOAD_DIRECTORY).resolve(nameImage).toAbsolutePath();
    }
}
