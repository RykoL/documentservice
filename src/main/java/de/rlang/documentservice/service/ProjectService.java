package de.rlang.documentservice.service;

import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.util.EntityToDTOConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    private final UserService userService;

    @Autowired
    public ProjectService(
            ProjectRepository projectRepository,
            UserService userService) {
       this.projectRepository = projectRepository;
       this.userService = userService;
    }

    @Transactional
    public ProjectInformationDTO createProject(CreateProjectDTO createProjectDTO) {

        User currentUser = userService.getCurrentAuthenticatedUser();

        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setCreator(currentUser);
        project.setCreatedAt(LocalDateTime.now());

        projectRepository.save(project);

        return EntityToDTOConverter.ConvertToProjectInformationDTO(project);
    }
}
