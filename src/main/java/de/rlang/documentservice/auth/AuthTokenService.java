package de.rlang.documentservice.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthTokenService {

    private Algorithm algorithm;

    public AuthTokenService() {
        this.algorithm = Algorithm.HMAC256("secret");
    }

    public String buildAuthenticationToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .sign(algorithm);
    }

    public JWTAuthenticationToken getAuthentication(String token) {

            String user = JWT.require(algorithm)
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();

            UUID userUuid = UUID.fromString(user);

            if (user != null) {
                return new JWTAuthenticationToken(new JWTUser(userUuid),  new ArrayList<>());
            }

            return null;
    }
}
