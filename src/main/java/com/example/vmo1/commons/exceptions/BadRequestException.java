package com.example.vmo1.commons.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestException extends RuntimeException {
    private String fieldName;
    private String message;

    public BadRequestException(String message, String fieldName) {
        super(String.format("[%s] : [%s] ", message, fieldName));
        this.message = message;
        this.fieldName = fieldName;

    }
}
