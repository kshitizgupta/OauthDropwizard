package com.kshitiz.oauthCore.auth.grants;

import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.KeyStore;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

public class ClientCredentialsGrant implements Grants {
    private final KeyStore keyStore;
    private final JwtTokenService tokenService;
    public ClientCredentialsGrant(
        final KeyStore keyStore,
        final JwtTokenService tokenService
    ) {
        this.keyStore = keyStore;
        this.tokenService = tokenService;
    }

    @Override
    public String issueToken() throws JoseException, MalformedClaimException {
        return tokenService.createJWT();
    }
}
