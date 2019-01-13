package de.rlang.documentservice.service;

import de.rlang.documentservice.auth.AuthTokenService;
import de.rlang.documentservice.model.dto.in.LoginDTO;
import de.rlang.documentservice.model.dto.out.LoginSuccessDTO;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.LoginRepository;
import de.rlang.documentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    private LoginRepository loginRepository;


    private AuthTokenService authTokenService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(LoginRepository loginRepository,
                                 AuthenticationManager authenticationManager,
                                 AuthTokenService authTokenService) {

        this.loginRepository = loginRepository;
        this.authenticationManager = authenticationManager;
        this.authTokenService = authTokenService;
    }

    public LoginSuccessDTO login(LoginDTO loginDTO, HttpServletResponse response) throws AuthenticationException{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

            User user = loginRepository.findByEmail(loginDTO.getEmail()).getUser();

            response.addHeader("Authorization", this.authTokenService.buildAuthenticationToken(user.getUserUuid().toString()));

            return new LoginSuccessDTO(user.getUserUuid(), user.getName(), user.getLastName());

    }
}
