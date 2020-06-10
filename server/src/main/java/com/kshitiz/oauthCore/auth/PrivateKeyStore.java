package com.kshitiz.oauthCore.auth;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jose4j.jwk.RsaJsonWebKey;

public class PrivateKeyStore {
    private Map<String, PrivateKey> keyMap = new HashMap<>();

    public void addKey(RsaJsonWebKey key) {
        keyMap.put(key.getKeyId(), key.getPrivateKey());
    }

    public Optional<PrivateKey> getKey(String keyId) {
        return Optional.ofNullable(keyMap.get(keyId));
    }
}
