package com.kshitiz.oauthCore.auth.grants;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

public interface Grants {
    public String issueToken() throws JoseException, MalformedClaimException;
}
