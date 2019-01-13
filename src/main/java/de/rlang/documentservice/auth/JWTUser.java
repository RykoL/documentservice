package de.rlang.documentservice.auth;

import lombok.Getter;

import java.util.UUID;

@Getter
public class JWTUser {

    private final UUID userUuid;

    public JWTUser(UUID uuid) {
        userUuid = uuid;
    }
}
