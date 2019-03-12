package de.rlang.documentservice.util;

import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.model.dto.out.UserDTO;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class EntityToDTOConverter {

    public static <T> T ConvertToUserDTO(User user, Class<T> userClass) {
        T userDTO;
        try {
            userDTO = userClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static ProjectInformationDTO ConvertToProjectInformationDTO(Project project) {
        ProjectInformationDTO projectInformationDTO = new ProjectInformationDTO();
        BeanUtils.copyProperties(project, projectInformationDTO);

        if (project.getCreator() !=  null) {
            projectInformationDTO.setCreator(ConvertToUserDTO(project.getCreator(), UserDTO.class));
        }

        if (project.getParticipants().size() != 0)  {
            HashSet<UserDTO> participants = new HashSet<>();
            for(User user : project.getParticipants()) {
                participants.add(ConvertToUserDTO(user, UserDTO.class));
            }

            projectInformationDTO.setParticipants(new ArrayList(participants));
        }

        return projectInformationDTO;
    }
}
