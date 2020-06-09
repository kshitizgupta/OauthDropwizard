package com.kshitiz.oauthCore.dao;

import static java.util.stream.Collectors.toSet;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kshitiz.oauthCore.model.Application;
import com.kshitiz.oauthCore.model.ApplicationStatus;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import javax.swing.plaf.nimbus.State;

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

    public Optional<Application> getApplication(final UUID clientId) {
        Statement query = QueryBuilder.select().from("application").where(QueryBuilder.eq("id", clientId));
        Row row = executeStatement(query).one();

        return Optional.ofNullable(row).map(data -> new Application(
            data.getUUID("id"),
            ApplicationStatus.valueOf(data.getString("status")),
            data.getString("name"),
            data.getSet("redirect_uris", String.class)
                .stream()
                .map(URI::create)
                .collect(toSet()),
            data.getString("hashed_secret")
        ));
    }
}
