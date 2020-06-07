package com.kshitiz.oauthCore.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    private final String name;
    private final Set<String> roles;
    private final String passwordHash;

    public User(final String name, final String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.roles = null;
    }

    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
        passwordHash = null;
    }
    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return (int) (Math.random() * 100);
    }

    public Set<String> getRoles() {
        return roles;
    }
}
