package com.kshitiz.oauthCore.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jose4j.jwk.RsaJsonWebKey;

public class KeyStore {
    private Map<String, RsaJsonWebKey> keyMap = new HashMap<>();

    public void addKey(RsaJsonWebKey key) {
        keyMap.put(key.getKeyId(), key);
    }

    public Optional<RsaJsonWebKey> getKey(String keyId) {
        return Optional.ofNullable(keyMap.get(keyId));
    }
}
