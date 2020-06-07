package com.kshitiz.oauthCore.auth;

import com.kshitiz.oauthCore.dao.AuthenticationDao;

public class AuthenticationService {
    private final AuthenticationDao dao;

    public AuthenticationService(final AuthenticationDao dao) {
        this.dao = dao;
    }

    public boolean authenticateUser(String email, String passwordHash) {
        return passwordHash.equals(dao.getPasswordHash(email));
    }
}
