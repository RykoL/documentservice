package de.rlang.documentservice.service;

import de.rlang.documentservice.exception.ForbiddenException;
import de.rlang.documentservice.model.entity.Document;
import de.rlang.documentservice.model.entity.DocumentRevision;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.DocumentRepository;
import de.rlang.documentservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FilesystemService filesystemService;

    @Transactional
    public void storeFile(UUID projectId, MultipartFile multipartFile) throws IOException {
        User currentUser = userService.getCurrentAuthenticatedUser();
        Project project = projectRepository.findFirstByUuid(projectId);

        if (!project.getParticipants().contains(currentUser)) {
            throw new ForbiddenException();
        }

        Path p = filesystemService.saveToDisk(multipartFile);

        DocumentRevision documentRevision = new DocumentRevision();
        documentRevision.setMimeType(multipartFile.getContentType());
        documentRevision.setStoragePath(p.toString());

        Document document = new Document();
        document.setName(multipartFile.getName());
        document.getDocumentRevisions().add(documentRevision);

        project.getDocuments().add(document);
        projectRepository.save(project);
    }
}
