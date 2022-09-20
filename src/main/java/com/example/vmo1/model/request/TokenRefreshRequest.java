package com.example.vmo1.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenRefreshRequest {
    @NotNull(message = "Refresh token is required")
    private String refreshToken;
}
