package de.rlang.documentservice.model.dto.in;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateProjectDTO {

    public CreateProjectDTO(String name) {
        this.name = name;
    }

    public CreateProjectDTO(String name, List<UserDTO> users) {
        this.name = name;
        this.users = users;
    }

    @Length(max = 50)
    @NotNull
    private String name;

    private List<UserDTO> users;
}
