package com.rail.response.system.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rail.response.system.exception.dto.ResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        var errorMessages = violations.stream()
                .map(violation -> {
                    String fieldPath = violation.getPropertyPath().toString();
                    String javaFieldName = fieldPath.replaceFirst("^arg\\d+\\.", "");

                    String jsonFieldName = getJsonPropertyName(violation, javaFieldName);
                    return jsonFieldName + ": " + violation.getMessage();
                }).toList();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseDTO.builder()
                        .message("Request body invalido.")
                        .errors(errorMessages)
                        .errorDate(LocalDateTime.now())
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .build())
                .build();
    }

    private String getJsonPropertyName(ConstraintViolation<?> violation, String fieldName) {
        try {
            Class<?> rootClass = violation.getRootBeanClass();
            Field field = rootClass.getDeclaredField(fieldName);
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            return annotation != null ? annotation.value() : fieldName;
        } catch (NoSuchFieldException e) {
            return fieldName; // fallback se não encontrar o campo
        }
    }
}