package com.kshitiz.oauthCore.auth.grants;

import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.PrivateKeyStore;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

public class ClientCredentialsGrant implements Grants {
    private final PrivateKeyStore privateKeyStore;
    private final JwtTokenService tokenService;
    public ClientCredentialsGrant(
        final PrivateKeyStore privateKeyStore,
        final JwtTokenService tokenService
    ) {
        this.privateKeyStore = privateKeyStore;
        this.tokenService = tokenService;
    }

    @Override
    public String issueToken() throws JoseException, MalformedClaimException {
        return tokenService.createJWT();
    }
}
