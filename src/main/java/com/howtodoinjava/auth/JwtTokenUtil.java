package com.howtodoinjava.auth;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

public class JwtTokenUtil {
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.
    }
}
