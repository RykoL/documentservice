package de.rlang.documentservice.controller;

import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.service.ProjectService;
import de.rlang.documentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProjectInformationDTO getProjectInformation(@PathVariable("projectId") String projectId) {
        ProjectInformationDTO projectInformationDTO = new ProjectInformationDTO();
        projectInformationDTO.setUuid(UUID.fromString(projectId));
        return projectInformationDTO;
    }

}

