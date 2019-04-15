package de.rlang.documentservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}")
public class DocumentController {


    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public void createDocument(@PathVariable("projectId") UUID projectId, @RequestParam("document") MultipartFile multipartFile) {

    }

    @RequestMapping(value = "/documents/{documentId}", method = RequestMethod.POST)
    public void uploadRevision(UUID projectId, UUID documentId) {

    }

    @RequestMapping(value = "/projects/{projectId}/documents/{documentId}", method = RequestMethod.GET)
    public void getDocumentInformation() {

    }
}
