package com.kshitiz.oauthCore.rest.dto;

import java.util.UUID;


public class CreateApplicationResponse {
    private final UUID uuid;
    private final String name;
    private final String secret;

    public CreateApplicationResponse(final UUID uuid, final String name, final String secret) {
        this.uuid = uuid;
        this.name = name;
        this.secret = secret;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSecret() {
        return secret;
    }
}
