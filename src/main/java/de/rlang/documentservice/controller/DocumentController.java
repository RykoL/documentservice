package de.rlang.documentservice.controller;

import de.rlang.documentservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public void createDocument(@PathVariable("projectId") UUID projectId, @RequestParam("document") MultipartFile multipartFile) {
        documentService.storeFile(projectId, multipartFile);
    }

    @RequestMapping(value = "/documents/{documentId}", method = RequestMethod.POST)
    public void uploadRevision(UUID projectId, UUID documentId) {

    }

    @RequestMapping(value = "/projects/{projectId}/documents/{documentId}", method = RequestMethod.GET)
    public void getDocumentInformation() {

    }
}
