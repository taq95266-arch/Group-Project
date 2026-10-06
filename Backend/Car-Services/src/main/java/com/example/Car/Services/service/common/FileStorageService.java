package com.example.Car.Services.service.common;


import com.example.Car.Services.expection.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;


    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Uploaded file is empty");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            File uploadDirectory = uploadPath.toFile();

            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            File destination = new File(uploadDirectory, fileName);
            file.transferTo(destination);

            return fileName;
        } catch (Exception e) {
            throw new BadRequestException("Failed to store file: " + e.getMessage());
        }
    }


}
