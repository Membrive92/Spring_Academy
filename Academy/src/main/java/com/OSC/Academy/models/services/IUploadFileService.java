package com.OSC.Academy.models.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {
    public Resource upload(String fileImage) throws MalformedURLException;
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String nameImage);
    public Path getPath(String nameImage);
}
