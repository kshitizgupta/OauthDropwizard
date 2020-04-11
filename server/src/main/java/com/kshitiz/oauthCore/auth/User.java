package com.kshitiz.oauthCore.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    private final String name;
    private final Set<String> roles;

    public User(final String name) {
        this.name = name;
        this.roles = null;
    }

    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
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
