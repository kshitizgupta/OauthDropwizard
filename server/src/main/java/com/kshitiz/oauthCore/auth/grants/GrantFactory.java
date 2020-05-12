package com.kshitiz.oauthCore.auth.grants;

import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GrantFactory {
    private Map<String, Grants> grantsMap = new HashMap<String, Grants>();

    public GrantFactory(KeyStore keyStore, JwtTokenService jwtTokenService) {
        grantsMap.put("client_credentials", new ClientCredentialsGrant(keyStore, jwtTokenService));
    }

    public Optional<Grants> getGrant(String type) {
        return Optional.ofNullable(grantsMap.get(type));
    }
}
