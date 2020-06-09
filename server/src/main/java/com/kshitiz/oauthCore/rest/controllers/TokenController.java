package com.kshitiz.oauthCore.rest.controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.kshitiz.oauthCore.auth.AuthenticationService;
import com.kshitiz.oauthCore.auth.grants.GrantFactory;
import com.kshitiz.oauthCore.auth.grants.Grants;
import com.kshitiz.oauthCore.dao.ApplicationDao;
import com.kshitiz.oauthCore.model.Application;
import com.kshitiz.oauthCore.rest.dto.TokenResponse;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.lang3.StringUtils;

@Path("/token")
public class TokenController {
    private final GrantFactory grantFactory;
    private final AuthenticationService authenticationService;
    private final ApplicationDao applicationDao;

    public TokenController(
        final GrantFactory grantFactory,
        final AuthenticationService authenticationService,
        final ApplicationDao applicationDao
    ) {
        this.grantFactory = grantFactory;
        this.authenticationService = authenticationService;
        this.applicationDao = applicationDao;
    }

    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public TokenResponse issueToken(
        @NotNull @FormParam("client_id") String clientIdParam,
        @NotNull @FormParam("client_secret") String clientSecret,
        @NotNull @FormParam("grant_type") String grantType,
        MultivaluedMap<String, String> formParams
    ) throws Exception {
        Grants grants = grantFactory.getGrant(grantType).orElseThrow(() -> new Exception("Illegal grant type"));

        boolean isValid = verifyCredentials(parseUUID(clientIdParam), clientSecret);

        if(!isValid) {
            throw new BadRequestException("Unauthorized client");
        }
        authenticationService.authenticateUser("abc", "abc");
        return new TokenResponse(grants.issueToken());
    }

    private boolean verifyCredentials(final UUID clientIdParam, final String clientSecret) {
        return StringUtils.isNotEmpty(clientSecret) && clientSecret.equals(getApplication(clientIdParam).getHashedSecret());
    }

    private Application getApplication(final UUID clientId) {
        return applicationDao.getApplication(clientId).orElseThrow(()-> new BadRequestException("invalid client id"));
    }

    private UUID parseUUID(String clientId) {
        try {
            return UUID.fromString(clientId);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Illegal client id provided");
        }
    }
}
