package com.kshitiz.oauthCore.auth;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PublicKeyStore {
    private Map<String, PublicKey> keyMap = new HashMap<>();

    public void setKey(String keyId, PublicKey key) {
        keyMap.put(keyId, key);
    }

    public Optional<PublicKey> getKey(String keyId) {
        return Optional.ofNullable(keyMap.get(keyId));
    }

}
