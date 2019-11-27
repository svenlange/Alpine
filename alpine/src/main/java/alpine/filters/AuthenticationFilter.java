/*
 * This file is part of Alpine.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) Steve Springett. All Rights Reserved.
 */
package alpine.filters;

import alpine.auth.ApiKeyAuthenticationService;
import alpine.auth.GitLabAuthenticationService;
import alpine.auth.JwtAuthenticationService;
import alpine.logging.Logger;
import org.glassfish.jersey.server.ContainerRequest;
import org.owasp.security.logging.SecurityMarkers;

import javax.annotation.Priority;
import javax.naming.AuthenticationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.security.Principal;

/**
 * A filter that ensures that all calls going through this filter are
 * authenticated. Exceptions are made for swagger URLs.
 *
 * @see AuthenticationFeature
 * @author Steve Springett
 * @since 1.0.0
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    // Setup logging
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext instanceof ContainerRequest) {
            final ContainerRequest request = (ContainerRequest) requestContext;

            // Bypass authentication for swagger
            if (request.getRequestUri().getPath().contains("/api/swagger")) {
                return;
            }

            Principal principal = null;

            final ApiKeyAuthenticationService apiKeyAuthService = new ApiKeyAuthenticationService(request);
            if (apiKeyAuthService.isSpecified()) {
                try {
                    principal = apiKeyAuthService.authenticate();
                } catch (AuthenticationException e) {
                    LOGGER.info(SecurityMarkers.SECURITY_FAILURE, "Invalid API key asserted");
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    return;
                }
            }

            final GitLabAuthenticationService gitlabAuthService = new GitLabAuthenticationService("", "", request);
            if (gitlabAuthService.isSpecified()) {
                try {
                    principal = gitlabAuthService.authenticate();
                } catch (AuthenticationException e) {
//                    LOGGER.info(SecurityMarkers.SECURITY_FAILURE, "Invalid API key asserted");
//                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//                    return;
                }
            }

            final JwtAuthenticationService jwtAuthService = new JwtAuthenticationService(request);
            if (jwtAuthService.isSpecified()) {
                try {
                    principal = jwtAuthService.authenticate();
                } catch (AuthenticationException e) {
                    LOGGER.info(SecurityMarkers.SECURITY_FAILURE, "Invalid JWT asserted");
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    return;
                }
            }



            if (principal == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            } else {
                requestContext.setProperty("Principal", principal);
            }
        }
    }

}
