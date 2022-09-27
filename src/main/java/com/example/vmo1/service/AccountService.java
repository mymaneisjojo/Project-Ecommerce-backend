package com.example.vmo1.service;

import com.example.vmo1.model.request.*;
import com.example.vmo1.model.response.AccountDtoToResponse;
import com.example.vmo1.model.response.AccountResponse;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.security.service.CustomUserDetails;

public interface AccountService {
    AccountDtoToResponse addAccountByAdmin(SignupRequest request);
    AccountResponse getAllAccount(String name, int pageNo, int pageSize);
    AccountDtoToResponse updateProfile(CustomUserDetails customUserDetails, UpdateAccountRequest request);
    AccountDtoToResponse updateAccountByAdmin(UpdateAccountByAdminRequest request, long id);
    void deleteAccountByAdmin(long id);
    MessageResponse updatePassword(CustomUserDetails customUserDetails, UpdatePasswordRequest updatePasswordRequest);
    AccountDto getAccountById(CustomUserDetails customUserDetails) ;
}
