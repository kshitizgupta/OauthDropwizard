package com.kshitiz.oauthCore.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraSession {
    private final Logger LOG = LoggerFactory.getLogger(CassandraSession.class.getName());
    private Session session;

    public CassandraSession() {
    }

    private void buildSession() {
        Cluster cluster = Cluster.builder()
            .withoutMetrics()
            .addContactPoint("cassandra")
            .build();
        Metadata metadata = cluster.getMetadata();
        LOG.info("Connected to cluster: {}\n", metadata.getClusterName());
        for (final Host host : metadata.getAllHosts()) {
            LOG.info("Datacenter: {}; Host: {}; Rack: {}}\n",
                host.getDatacenter(), host.getAddress(), host.getRack()
            );
        }
        session = cluster.connect("authz");
    }

    public Session getSession() {
        if (session == null) {
            buildSession();
        }
        return session;
    }
}
