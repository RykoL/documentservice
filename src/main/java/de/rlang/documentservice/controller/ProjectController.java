package de.rlang.documentservice.controller;

import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.service.ProjectService;
import de.rlang.documentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private UserService userService;

    private ProjectService projectService;

    @Autowired
    public ProjectController(
            UserService userService,
            ProjectService projectService) {

        this.userService = userService;
        this.projectService = projectService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ProjectInformationDTO> createProject(@Valid @RequestBody CreateProjectDTO createProjectDTO) {
        ProjectInformationDTO projectInformationDTO = projectService.createProject(createProjectDTO);

        return ResponseEntity.status(201).body(projectInformationDTO);
    }

}

