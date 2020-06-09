package com.kshitiz.oauthCore.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public static String hashValue(String value) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
            final byte[] hash = md.digest(valueBytes);

            final StringBuilder hexValue = new StringBuilder();
            for (byte b : hash) {
                hexValue.append(String.format("%02x", b));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}
