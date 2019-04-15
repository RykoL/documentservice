package de.rlang.documentservice.service;

import de.rlang.documentservice.repository.DocumentRepository;
import de.rlang.documentservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DocumentRepository documentRepository;

    public void storeFile(MultipartFile multipartFile) {

    }
}
