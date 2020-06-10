package com.kshitiz.oauthCore.auth.grants;

import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.PrivateKeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GrantFactory {
    private Map<String, Grants> grantsMap = new HashMap<String, Grants>();

    public GrantFactory(PrivateKeyStore privateKeyStore, JwtTokenService jwtTokenService) {
        grantsMap.put("client_credentials", new ClientCredentialsGrant(privateKeyStore, jwtTokenService));
    }

    public Optional<Grants> getGrant(String type) {
        return Optional.ofNullable(grantsMap.get(type));
    }
}
