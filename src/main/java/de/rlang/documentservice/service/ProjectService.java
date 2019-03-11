package de.rlang.documentservice.service;

import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

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

    public ProjectInformationDTO getProject(UUID projectId) {
        Project p = projectRepository.findFirstByUuid(projectId);

        return EntityToDTOConverter.ConvertToProjectInformationDTO(p);
    }

    public List<ProjectInformationDTO> getAllProjects() {
        User currentUser = userService.getCurrentAuthenticatedUser();

        List<Project> projects = projectRepository.findAllByCreator(currentUser);

        return projects.stream()
                .map(EntityToDTOConverter::ConvertToProjectInformationDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.findFirstByUuid(projectId);

        if (project != null) {
            projectRepository.delete(project);
        }
    }
}
