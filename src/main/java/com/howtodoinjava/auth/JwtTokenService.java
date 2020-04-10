package com.howtodoinjava.auth;

import com.sun.jdi.request.InvalidRequestStateException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.glassfish.jersey.client.authentication.RequestAuthenticationException;
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
import org.jose4j.jwx.JsonWebStructure;
import org.jose4j.lang.JoseException;

public class JwtTokenService {
    public static String ISSUER = "SampleOauthService";
    private final KeyStore keyStore = new KeyStore();
    final String AUDIENCE = "Audience";

    public String createJWT()
        throws JoseException, MalformedClaimException {
        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        rsaJsonWebKey.setKeyId(String.valueOf(new Date().getTime()));

        keyStore.addKey(rsaJsonWebKey);
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

        return jwt;
    }

    private boolean verifyToken(final String jwtTokenStr, final RsaJsonWebKey rsaJsonWebKey)
        throws MalformedClaimException {

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setRequireExpirationTime() // the JWT must have an expiration time
            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for
            // clock skew
            .setRequireSubject() // the JWT must have a subject claim
            .setExpectedIssuer(ISSUER) // whom the JWT needs to have been issued by
            .setExpectedAudience(AUDIENCE) // to whom the JWT is intended for
            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
            .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                AlgorithmConstraints.ConstraintType.WHITELIST,
                AlgorithmIdentifiers.RSA_USING_SHA256
            ) // which is only RS256 here
            .build(); // create the JwtConsumer instance

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtTokenStr);
            System.out.println("JWT validation succeeded! " + jwtClaims);
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            System.out.println("Invalid JWT! " + e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired()) {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
            return false;
        }

        return true;
    }

    public boolean verifyToken(String jwtTokenStr) throws Exception {
        return verifyToken(jwtTokenStr, keyStore.getKey(getKeyId(jwtTokenStr)).orElseThrow(() -> new Exception("Invalid jwt")));
    }

    private String getKeyId(String jwt) throws Exception {
        if(jwt ==null || jwt.isEmpty()) {
            throw new Exception("Invalid access token");
        }

        List<JsonWebStructure> jws =
            new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setSkipSignatureVerification()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer(ISSUER)
                .setExpectedAudience(AUDIENCE)
                .build()
                .process(jwt)
                .getJoseObjects();

        return jws.stream()
            .findFirst()
            .map(JsonWebStructure::getKeyIdHeaderValue)
            .map(String::new)
            .orElseThrow(() -> new Exception(
                "No key information found in the provided access token!"));
    }

    public static void main(String[] args) throws Exception {
        JwtTokenService tokenUtil = new JwtTokenService();
        String jwt = tokenUtil.createJWT();
        System.out.println("JWT obtained is:" + jwt);
        System.out.println(tokenUtil.verifyToken(jwt));
    }
}
