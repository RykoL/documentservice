package de.rlang.documentservice.model.dto.in;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID userUuid;

    private String name;

    private String lastName;
}
