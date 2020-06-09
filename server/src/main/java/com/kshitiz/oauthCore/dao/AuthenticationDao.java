package com.kshitiz.oauthCore.dao;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kshitiz.oauthCore.auth.User;

public class AuthenticationDao {
    private final CassandraSession session;

    public AuthenticationDao(final CassandraSession session) {
        this.session = session;
    }

    public void createUser(User user) {
    }

    public String getPasswordHash(String email) {
        Statement query = QueryBuilder
            .select("password_hash")
            .from("user")
            .where(QueryBuilder.eq("email", email));

        return session.getSession().execute(query).one().toString();
    }
}
