package de.rlang.documentservice.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private AuthTokenService authTokenService;

    public JWTAuthenticationFilter() {
        this.authTokenService = new AuthTokenService();
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        JWTAuthenticationToken token = authTokenService.getAuthentication(authHeader);

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }


}
