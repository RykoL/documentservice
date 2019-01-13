package de.rlang.documentservice.model.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private UUID userUuid;

    private String name;

    private String lastName;
}
