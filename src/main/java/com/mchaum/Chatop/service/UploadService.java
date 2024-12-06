package com.mchaum.Chatop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${upload.dir}")
    private String uploadDir; 

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + File.separator + fileName);

        Files.createDirectories(path.getParent());

        Files.write(path, file.getBytes());

        return "http://localhost:9000/uploads/" + fileName;
    }
}
