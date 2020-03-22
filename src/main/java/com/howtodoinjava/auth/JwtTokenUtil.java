package com.howtodoinjava.auth;

import java.util.Arrays;
import java.util.List;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

public class JwtTokenUtil {
    public static String ISSUER = "DropwizardDemo";

    public static String createJWT()
        throws JoseException, MalformedClaimException {
        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

        // Give the JWK a key ID (kid)
        rsaJsonWebKey.setKeyId("k1");

        // Create the claims
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(ISSUER);
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(10);
        //        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(1);
        claims.setSubject("subject");
        claims.setClaim("email", "someemail@somedomain.com");
        List<String> groups = Arrays.asList("group-1", "group-2");
        claims.setStringListClaim("groups", groups);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = jws.getCompactSerialization();

        System.out.println("JWT: " + jwt);

        verifyJwt(rsaJsonWebKey, jwt);
        return jwt;
    }

    private static void verifyJwt(final RsaJsonWebKey rsaJsonWebKey, final String jwt) throws MalformedClaimException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setRequireExpirationTime()
            .setAllowedClockSkewInSeconds(30)
            .setRequireSubject()
            .setExpectedIssuer(ISSUER)
            .setExpectedAudience("Audience")
            .setVerificationKey(rsaJsonWebKey.getKey())
            .setJwsAlgorithmConstraints(
                AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256
            )
            .build();

        try {
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
        } catch (InvalidJwtException e) {
            System.out.println("Invalid JWT! " + e);

            if (e.hasExpired()) {
                System.out.println("\n\nJWT expired.at" + e.getJwtContext().getJwtClaims().getExpirationTime());
            }

            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("\n\nJWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
        }
    }

    public static void main(String[] args) throws JoseException, MalformedClaimException {
        System.out.println(createJWT());
    }
}
