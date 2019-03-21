package de.rlang.documentservice.helper;

import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.dto.out.UserDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
public class DTOFactory {

    public ProjectInformationDTO buildProjectInformationDTO() {
        return new ProjectInformationDTO();
    }

    public UserDTO buildUserDTO(User user) {
        return new UserDTO(
                TestConstants.NULL_UUID,
                "User",
                "Test");
    }

    public User buildDefaultUser() {
        User user = new User();
        user.setUserUuid(TestConstants.NULL_UUID);
        user.setName("User");
        user.setLastName("Test");

        return user;
    }

    public User buildUserWithRandomUUID() {
        User user = new User();
        user.setName("RandomUser");
        user.setLastName("RandomLastName");
        user.fill();

        return user;
    }

    public Project buildProject(String name, User owner) {
        Project project = new Project();
        project.setName(name);
        project.setId((long)Math.random());
        project.setUuid(UUID.randomUUID());
        project.setParticipants(new HashSet<>());
        project.setCreator(owner);

        return project;
    }
}
