package de.rlang.documentservice.helper;

import de.rlang.documentservice.auth.AuthTokenService;
import de.rlang.documentservice.model.entity.User;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthHelper {

    @LocalServerPort
    private int port;

    private static final String TEST_USER_EMAIL = "john.doe@test.com";
    private static final String TEST_USER_PW = "12345";

    AuthTokenService authTokenService;
    private DTOFactory dtoFactory;

    public AuthHelper() {
        authTokenService = new AuthTokenService();
        dtoFactory = new DTOFactory();
    }

    public String getAuthToken() {
        return authTokenService.buildAuthenticationToken(dtoFactory.buildUserDTO(dtoFactory.buildDefaultUser()).getUserUuid().toString());
    }

    public String getAuthToken(User user) {
        return authTokenService.buildAuthenticationToken(user.getUserUuid().toString());
    }

    public HttpHeaders getAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAuthToken());
        return headers;
    }

    public HttpHeaders getAuthHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAuthToken(user));
        return headers;
    }
}
