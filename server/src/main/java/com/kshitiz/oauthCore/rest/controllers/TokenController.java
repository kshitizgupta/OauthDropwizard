package com.kshitiz.oauthCore.rest.controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.kshitiz.oauthCore.auth.AuthenticationService;
import com.kshitiz.oauthCore.auth.grants.GrantFactory;
import com.kshitiz.oauthCore.auth.grants.Grants;
import com.kshitiz.oauthCore.rest.dto.TokenResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

@Path("/token")
public class TokenController {
    private final GrantFactory grantFactory;
    private final AuthenticationService authenticationService;

    public TokenController(
        final GrantFactory grantFactory,
        final AuthenticationService authenticationService
    ) {
        this.grantFactory = grantFactory;
        this.authenticationService = authenticationService;
    }

    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public TokenResponse issueToken(
        @NotNull @FormParam("client_id") String clientIdParam,
        @NotNull @FormParam("grant_type") String grantType,
        MultivaluedMap<String, String> formParams
    ) throws Exception {
        Grants grants = grantFactory.getGrant(grantType).orElseThrow(() -> new Exception("Illegal grant type"));
        authenticationService.authenticateUser("abc", "abc");
        return new TokenResponse(grants.issueToken());
    }
}
