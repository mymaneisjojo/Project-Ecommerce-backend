package com.example.vmo1.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdatePasswordRequest {
    @NotNull(message = "Old password is confirm")
    private String oldPassword;
    @NotNull(message = "New password is confirm")
    private String newPassword;
}
