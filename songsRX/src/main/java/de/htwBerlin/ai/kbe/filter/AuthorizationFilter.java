package de.htwBerlin.ai.kbe.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.htwBerlin.ai.kbe.storage.Token;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String token =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(token == null) {
            abortWithUnauthorized(requestContext);
            return;
        }

        if(!isTokenValid(token))
        	abortWithUnauthorized(requestContext);
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN).entity("You are not connected!").build());
    }

    private boolean isTokenValid(String token) {
       return Token.getInstance().isTokenvalid(token);
    }
}
