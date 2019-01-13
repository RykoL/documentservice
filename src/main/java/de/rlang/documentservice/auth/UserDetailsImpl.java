package de.rlang.documentservice.auth;

import de.rlang.documentservice.model.entity.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private String username;

    private String password;

    private Login loginInformation;

    UserDetailsImpl(Login login) {
        this.username = login.getEmail();
        this.password = login.getPassword();
        this.loginInformation = login;
    }

    public Login getLoginInformation() {
        return this.loginInformation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
