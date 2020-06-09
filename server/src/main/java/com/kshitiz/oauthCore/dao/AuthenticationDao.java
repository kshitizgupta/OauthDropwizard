package com.kshitiz.oauthCore.dao;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kshitiz.oauthCore.model.User;

public class AuthenticationDao extends AbstractDao {
    public AuthenticationDao(final CassandraSession session) {
        super(session);
    }

    public void createUser(User user) {
    }

    public String getPasswordHash(String email) {
        Statement query = QueryBuilder
            .select("password_hash")
            .from("user")
            .where(QueryBuilder.eq("email", email));

        return executeStatement(query).one().toString();
    }
}
