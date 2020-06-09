package com.kshitiz.oauthCore.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;

public class AbstractDao {
    private final CassandraSession session;

    public AbstractDao(final CassandraSession session) {
        this.session = session;
    }

    protected ResultSet executeStatement(Statement stmt) {
        return session.getSession().execute(stmt);
    }
}

