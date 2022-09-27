package com.example.vmo1.commons.exceptions;

public class InvalidIdException extends RuntimeException{
    private long fieldName;
    private String message;

    public InvalidIdException(String message, long fieldName) {
        super(String.format("[%s] : [%s] ", message, fieldName));
        this.message = message;
        this.fieldName = fieldName;

    }
}
