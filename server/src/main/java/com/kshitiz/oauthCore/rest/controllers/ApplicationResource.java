package com.kshitiz.oauthCore.rest.controllers;

import com.kshitiz.oauthCore.dao.ApplicationDao;
import com.kshitiz.oauthCore.model.Application;
import com.kshitiz.oauthCore.rest.dto.CreateApplicationResponse;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/application")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
    private ApplicationDao applicationDao;

    public ApplicationResource(final ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    @POST
    @RolesAllowed("ADMIN")
    public CreateApplicationResponse createApplication(
        @Valid Application application
    ) {
        applicationDao.createApplication(application);
        return new CreateApplicationResponse(application.getUuid(), application.getName(), application.getHashedSecret());
    }
}
