package de.rlang.documentservice.service;

import de.rlang.documentservice.auth.JWTUser;
import de.rlang.documentservice.exception.UnauthorizedException;
import de.rlang.documentservice.model.dto.in.RegisterDTO;
import de.rlang.documentservice.model.entity.Login;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.LoginRepository;
import de.rlang.documentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private LoginRepository loginRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(LoginRepository loginRepository,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {

        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(RegisterDTO registerDTO) throws Exception {
        Login login = loginRepository.findByEmail(registerDTO.getEmail());

        if (login != null) {
            throw new Exception("Login already created");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setLastName(registerDTO.getLastName());
        userRepository.save(user);

        login = new Login();
        login.setEmail(registerDTO.getEmail());
        login.setPassword(encryptPassword(registerDTO.getPassword()));
        login.setUser(user);
        loginRepository.save(login);
    }

    public User getCurrentAuthenticatedUser() {
        JWTUser principal = (JWTUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findFirstByUserUuid(principal.getUserUuid());
        if (user == null) {
            throw new UnauthorizedException();
        }
        return user;
    }

    private String encryptPassword(String clearText) {
        return passwordEncoder.encode(clearText);
    }
}
