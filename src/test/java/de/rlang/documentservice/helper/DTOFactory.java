package de.rlang.documentservice.helper;

import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.dto.out.UserDTO;
import de.rlang.documentservice.model.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DTOFactory {

    public ProjectInformationDTO buildProjectInformationDTO() {
        return new ProjectInformationDTO();
    }

    public UserDTO buildUserDTO() {
        return new UserDTO(
                TestConstants.NULL_UUID,
                "User",
                "Test");
    }

    public User buildUser() {
        UserDTO userDTO = buildUserDTO();
        User user = new User();

        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
