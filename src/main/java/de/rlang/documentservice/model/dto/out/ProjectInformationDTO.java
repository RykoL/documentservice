package de.rlang.documentservice.model.dto.out;

import de.rlang.documentservice.model.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class ProjectInformationDTO {

    private UUID uuid;

    private String name;

    private UserDTO creator;

    private LocalDateTime createdAt;

    private Set<UserDTO> participants;
}
