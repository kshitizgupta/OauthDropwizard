package com.howtodoinjava.auth;

import java.security.Key;
import java.util.Arrays;
import java.util.List;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

public class JwtTokenUtil {
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) throws JoseException {
        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

        // Give the JWK a key ID (kid)
        rsaJsonWebKey.setKeyId("k1");

        // Create the claims
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Issuer");
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(10);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(1);
        claims.setSubject("subject");
        claims.setClaim("email", "someemail@somedomain.com");
        List<String> groups = Arrays.asList("group-1", "group-2");
        claims.setStringListClaim("groups", groups);

        
    }
}
