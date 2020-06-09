package com.kshitiz.oauthCore.model;

import static java.util.UUID.randomUUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kshitiz.oauthCore.auth.Hasher;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;

public class Application {

    private final UUID uuid;

    private ApplicationStatus status;

    @NotEmpty
    private String name;

    private Set<URI> redirectUris;

    private String hashedSecret;

    @JsonCreator
    public static Application create(
        @JsonProperty("name") String name,
        @JsonProperty("redirectUris") Set<URI> redirectUris
    ) {
        return new Application(
            randomUUID(),
            name,
            redirectUris
        );
    }

    public Application(
        UUID uuid,
        String name,
        Set<URI> redirectUris
    ) {
        this.uuid = uuid;
        this.name = name;
        this.hashedSecret = randomUUID().toString();
        this.redirectUris = redirectUris;
        this.status = ApplicationStatus.ENABLED;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean verifySecret(String secret) {
        return secret != null && Hasher.hashValue(secret).equals(hashedSecret);
    }

    @JsonIgnore
    public String getHashedSecret() {
        return this.hashedSecret;
    }

    public String generateAndGetSecret() {
        String secret = randomUUID().toString();
        this.hashedSecret = Hasher.hashValue(secret);
        return secret;
    }

    public Set<URI> getRedirectUris() {
        return redirectUris;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Application{");
        sb.append("uuid=").append(uuid);
        sb.append(", status=").append(status);
        sb.append(", name='").append(name).append('\'');
        sb.append(", redirectUris=").append(redirectUris);
        sb.append('}');
        return sb.toString();
    }
}
