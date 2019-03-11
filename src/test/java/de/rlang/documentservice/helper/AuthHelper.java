package de.rlang.documentservice.helper;

import de.rlang.documentservice.auth.AuthTokenService;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthHelper {

    @LocalServerPort
    private int port;

    private static final String TEST_USER_EMAIL = "john.doe@test.com";
    private static final String TEST_USER_PW = "12345";

    AuthTokenService authTokenService;

    public AuthHelper() {
        authTokenService = new AuthTokenService();
    }

    public String getAuthToken() {
        DTOFactory dtoFactory = new DTOFactory();

        return authTokenService.buildAuthenticationToken(dtoFactory.buildUserDTO().getUserUuid().toString());
    }

    public HttpHeaders getAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAuthToken());
        return headers;
    }
}
