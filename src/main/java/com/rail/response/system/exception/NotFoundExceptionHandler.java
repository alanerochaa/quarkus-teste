package com.rail.response.system.exception;

import com.rail.response.system.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ResponseDTO.builder()
                        .message(exception.getMessage())
                        .errorDate(LocalDateTime.now())
                        .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .build())
                .build();
    }
}
