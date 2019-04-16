package de.rlang.documentservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesystemService {

    @Value("${uploadDirectory}")
    private String baseUploadPath;

    public Path saveToDisk(MultipartFile multipartFile) throws IOException {
        Path p = Paths.get(baseUploadPath, multipartFile.getOriginalFilename());

        try(FileOutputStream fileOutputStream = new FileOutputStream(p.toFile())) {
            fileOutputStream.write(multipartFile.getBytes());
        }

        return p;
    }
}
