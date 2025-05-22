package com.rail.response.system.exception;

import com.rail.response.system.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ResponseDTO.builder()
                        .message("An unexpected error occurred")
                        .errorDate(LocalDateTime.now())
                        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();
    }
}
