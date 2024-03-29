package de.rlang.documentservice.service;

import de.rlang.documentservice.exception.ForbiddenException;
import de.rlang.documentservice.exception.ResourceNotFoundException;
import de.rlang.documentservice.exception.UnauthorizedException;
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
        project.getParticipants().add(currentUser);

        projectRepository.save(project);

        return EntityToDTOConverter.ConvertToProjectInformationDTO(project);
    }

    public ProjectInformationDTO getProject(UUID projectId) {
        User currentUser = userService.getCurrentAuthenticatedUser();

        Project p = projectRepository.findFirstByUuid(projectId);

        if (currentUser == p.getCreator() || p.getParticipants().contains(currentUser)) {
            return EntityToDTOConverter.ConvertToProjectInformationDTO(p);
        }

        if (p == null) {
            throw new ResourceNotFoundException();
        }
        throw new ForbiddenException();
    }

    public List<ProjectInformationDTO> getAllProjects() {
        User currentUser = userService.getCurrentAuthenticatedUser();

        List<Project> projects = projectRepository.findAllByCreator(currentUser);

        return projects.stream()
                .map(EntityToDTOConverter::ConvertToProjectInformationDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteProject(UUID projectId) {
        User currentUser = userService.getCurrentAuthenticatedUser();

        Project project = projectRepository.findFirstByUuid(projectId);

        if (currentUser == null) {
            throw new UnauthorizedException();
        }

        if (!currentUser.getUserUuid().equals(project.getCreator().getUserUuid())) {
            throw new ForbiddenException();
        }

        if (project == null) {
            throw new ResourceNotFoundException();
        }

        projectRepository.delete(project);
    }
}
