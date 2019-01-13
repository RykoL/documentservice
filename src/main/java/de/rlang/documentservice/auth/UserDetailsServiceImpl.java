package de.rlang.documentservice.auth;

import de.rlang.documentservice.model.entity.Login;
import de.rlang.documentservice.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Login login = loginRepository.findByEmail(s);

        if (login == null) {
            throw new UsernameNotFoundException("User doesn't exist.");
        }

        return new UserDetailsImpl(login);
    }

}
