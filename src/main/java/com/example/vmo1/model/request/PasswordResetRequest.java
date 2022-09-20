package com.example.vmo1.model.request;

import com.example.vmo1.validation.annotation.MatchPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@MatchPassword
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Confirm password is required")
    private String confirmPassword;
    @NotNull(message = "Token is required")
    private String token;
}
