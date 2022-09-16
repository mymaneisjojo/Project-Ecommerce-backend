package com.example.vmo1.validation.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;
@Component
public class UserNameValidator implements Predicate<String> {

    public final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    @Override
    public boolean test(String username) {
        return username.matches(USERNAME_PATTERN);
    }
}
