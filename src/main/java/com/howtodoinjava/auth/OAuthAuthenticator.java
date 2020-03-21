package com.howtodoinjava.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;

public class OAuthAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(final String s) throws AuthenticationException {
        return Optional.empty();
    }
}
