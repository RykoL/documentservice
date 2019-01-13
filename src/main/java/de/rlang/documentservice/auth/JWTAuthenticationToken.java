package de.rlang.documentservice.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private JWTUser user;

    public JWTAuthenticationToken(
            JWTUser user,
            Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.user = user;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public JWTUser getPrincipal() {
        return user;
    }
}
