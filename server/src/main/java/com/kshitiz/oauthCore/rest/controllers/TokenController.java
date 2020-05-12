package com.kshitiz.oauthCore.rest.controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.kshitiz.oauthCore.auth.JwtTokenService;
import com.kshitiz.oauthCore.auth.KeyStore;
import com.kshitiz.oauthCore.auth.grants.GrantFactory;
import com.kshitiz.oauthCore.auth.grants.Grants;
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

    public TokenController(final GrantFactory grantFactory) {
        this.grantFactory = grantFactory;
    }

    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public String issueToken(
        @NotNull @FormParam("client_id") String clientIdParam,
        @NotNull @FormParam("grant_type") String grantType,
        MultivaluedMap<String, String> formParams
    ) throws Exception {

        Grants grants = grantFactory.getGrant(grantType).orElseThrow(() -> new Exception("Illegal grant type"));
        return grants.issueToken();
    }
}
