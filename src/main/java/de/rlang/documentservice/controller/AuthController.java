package de.rlang.documentservice.controller;


import de.rlang.documentservice.model.dto.in.LoginDTO;
import de.rlang.documentservice.model.dto.in.RegisterDTO;
import de.rlang.documentservice.model.dto.out.LoginSuccessDTO;
import de.rlang.documentservice.service.AuthenticationService;
import de.rlang.documentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private AuthenticationService authenticationService;

    private UserService userService;

    @Autowired
    public AuthController(
            AuthenticationService authenticationService,
            UserService userService) {

        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody RegisterDTO registerDTO) throws Exception {
        userService.saveUser(registerDTO);

        return  ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public LoginSuccessDTO login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return authenticationService.login(loginDTO, response);
    }
}
