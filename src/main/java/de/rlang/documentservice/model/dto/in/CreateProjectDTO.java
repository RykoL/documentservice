package de.rlang.documentservice.model.dto.in;


import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class CreateProjectDTO {

    public CreateProjectDTO(String name) {
        this.name = name;
    }

    @Length(max = 50)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }
}
