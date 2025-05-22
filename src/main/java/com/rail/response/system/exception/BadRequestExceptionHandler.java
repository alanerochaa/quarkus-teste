package com.rail.response.system.exception;

import com.rail.response.system.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseDTO.builder()
                        .message(exception.getMessage())
                        .errorDate(LocalDateTime.now())
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .build())
                .build();
    }
}