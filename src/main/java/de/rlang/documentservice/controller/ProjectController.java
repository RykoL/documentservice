package de.rlang.documentservice.controller;

import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ExposableControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(
            ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ProjectInformationDTO> createProject(@Valid @RequestBody CreateProjectDTO createProjectDTO) {
        ProjectInformationDTO projectInformationDTO = projectService.createProject(createProjectDTO);

        return ResponseEntity.status(201).body(projectInformationDTO);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ProjectInformationDTO getProjectInformation(@PathVariable("projectId") UUID projectId) {
        return projectService.getProject(projectId);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable("projectId") UUID projectId) throws Exception {
        projectService.deleteProject(projectId);

        return ResponseEntity.status(204).build();
    }
}

