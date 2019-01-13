package de.rlang.documentservice.model.dto.in;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
