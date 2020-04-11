package com.kshitiz.oauthCore.auth;

import io.dropwizard.auth.Authorizer;

public class BasicAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(final User user, final String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
