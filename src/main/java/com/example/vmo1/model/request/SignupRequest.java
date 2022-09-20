package com.example.vmo1.model.request;

import com.example.vmo1.model.entity.Role;
import com.example.vmo1.validation.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    @NotNull(message = "Username is required")
    private  String username;
    private String fullname;
    @NotNull(message = "Email is required")
    private  String email;
    @ValidPassword
    @NotNull
    private  String password;
    private String phone;
    private Set<Role> role;
}
