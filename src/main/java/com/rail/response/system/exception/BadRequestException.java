package com.rail.response.system.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Contrato inválido");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
