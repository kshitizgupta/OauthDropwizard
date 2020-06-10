package com.kshitiz.oauthCore;

import com.google.common.collect.ImmutableList;
import com.kshitiz.oauthCore.auth.AuthenticationService;
import com.kshitiz.oauthCore.auth.BasicAuthenticator;
import com.kshitiz.oauthCore.auth.BasicAuthorizer;
import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.PrivateKeyStore;
import com.kshitiz.oauthCore.auth.OAuthAuthenticator;
import com.kshitiz.oauthCore.dao.ApplicationDao;
import com.kshitiz.oauthCore.model.User;
import com.kshitiz.oauthCore.auth.grants.GrantFactory;
import com.kshitiz.oauthCore.dao.AuthenticationDao;
import com.kshitiz.oauthCore.dao.CassandraSession;
import com.kshitiz.oauthCore.dao.EmployeeDb;
import com.kshitiz.oauthCore.rest.controllers.ApplicationResource;
import com.kshitiz.oauthCore.rest.controllers.EmployeeResource;
import com.kshitiz.oauthCore.rest.controllers.TokenResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(final Configuration configuration, final Environment e) throws Exception {
        JwtTokenService tokenService = new JwtTokenService();
        PrivateKeyStore privateKeyStore = new PrivateKeyStore();

        GrantFactory grantFactory = new GrantFactory(privateKeyStore, tokenService);

        CassandraSession session = new CassandraSession();

        AuthenticationDao authenticationDao = new AuthenticationDao(session);

        AuthenticationService authenticationService = new AuthenticationService(authenticationDao);

        e.jersey().register(new EmployeeResource(new EmployeeDb()));
        final ApplicationDao applicationDao = new ApplicationDao(session);
        e.jersey().register(new ApplicationResource(applicationDao));
        e.jersey().register(new TokenResource(grantFactory, authenticationService, applicationDao));
        //****** Dropwizard security - custom classes ***********/
        AuthFilter<BasicCredentials, User> basicAuthenticator = new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new BasicAuthenticator())
            .setAuthorizer(new BasicAuthorizer())
            .setRealm("BASIC-AUTH-REALM")
            .buildAuthFilter();

        AuthFilter<String, User> oauthCredentialAuthFilter = new OAuthCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new OAuthAuthenticator())
//            .setAuthorizer(new ExampleAuthorizer())
            .setPrefix("Bearer")
            .buildAuthFilter();

        e.jersey().register(new AuthDynamicFeature(new ChainedAuthFilter<>(
            ImmutableList.of(basicAuthenticator, oauthCredentialAuthFilter)
        )));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
