package com.rail.response.system.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyApiKeyFilter implements ContainerRequestFilter {

    // Injeta a chave de API diretamente do application.properties
    @ConfigProperty(name = "api.key")
    String apiKey;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String apiKeyRequest = requestContext.getHeaderString("X-API-key");
    }
}


