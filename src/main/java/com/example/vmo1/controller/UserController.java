package com.example.vmo1.controller;

import com.example.vmo1.model.request.UpdateAccountRequest;
import com.example.vmo1.model.request.UpdatePasswordRequest;
import com.example.vmo1.model.response.AccountDtoToResponse;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.AccountService;
import com.example.vmo1.validation.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/profile-update")
    public ResponseEntity<?> updateProfile(@CurrentUser CustomUserDetails customUserDetails,@Valid @RequestBody UpdateAccountRequest updateAccountRequest){
        AccountDtoToResponse accountResponse = accountService.updateProfile(customUserDetails, updateAccountRequest);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @PutMapping("/password-update")
    public ResponseEntity<?> updateAccountPassword(@CurrentUser CustomUserDetails customUserDetails,@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        return ResponseEntity.ok(accountService.updatePassword(customUserDetails, updatePasswordRequest));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getAccountById(@CurrentUser CustomUserDetails customUserDetails){
        return ResponseEntity.ok(accountService.getAccountById(customUserDetails));
    }
}
