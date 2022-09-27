package com.example.vmo1.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MailSendException extends RuntimeException {

    private final String email;
    private final String message;

    public MailSendException(String email, String message) {
        super(String.format("Error sending [%s] for user [%s]", message, email));
        this.email = email;
        this.message = message;
    }
}
