package com.kshitiz.oauthCore.dao;

import static java.util.stream.Collectors.toSet;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kshitiz.oauthCore.model.Application;
import java.net.URI;

public class ApplicationDao extends AbstractDao {
    public ApplicationDao(final CassandraSession session) {
        super(session);
    }

    public void createApplication(Application application) {
        Insert insert = QueryBuilder.insertInto("application")
            .value("id", application.getUuid())
            .value("hashed_secret", application.getHashedSecret())
            .value("status", application.getStatus().name())
            .value("redirect_uris", application.getRedirectUris().stream().map(URI::toString).collect(toSet()))
            .value("name", application.getName());

        executeStatement(insert);
    }
}
