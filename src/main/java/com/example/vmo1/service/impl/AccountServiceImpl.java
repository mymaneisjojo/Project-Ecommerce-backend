package com.example.vmo1.service.impl;


import com.example.vmo1.commons.configs.MapperUtil;
import com.example.vmo1.commons.exceptions.BadRequestException;
import com.example.vmo1.commons.exceptions.InvalidIdException;
import com.example.vmo1.commons.exceptions.ResourceAlreadyInUseException;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.Role;
import com.example.vmo1.model.request.*;
import com.example.vmo1.model.response.AccountDtoToResponse;
import com.example.vmo1.model.response.AccountResponse;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.RoleRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.AccountService;
import com.example.vmo1.validation.validator.EmailValidator;
import com.example.vmo1.validation.validator.PhoneValidator;
import com.example.vmo1.validation.validator.UserNameValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private PhoneValidator phoneValidator;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AccountDtoToResponse addAccountByAdmin(SignupRequest request) {
        Account accountRegister = MapperUtil.map(request, Account.class);
        boolean emailExists = accountRepository.findByEmail(request.getEmail()).isPresent();
        boolean usernameExists = accountRepository.findByUsername(request.getUsername()).isPresent();
        if(emailExists || usernameExists){
            throw new ResourceAlreadyInUseException("Account", "Email or username", request.getEmail() + " or " + request.getUsername());
        }
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidUserName = userNameValidator.test(request.getUsername());
        if (isValidEmail && isValidUserName) {
            if (!StringUtils.isEmpty(request.getPhone())) {
                boolean isValidPhone = phoneValidator.test(request.getPhone());
                if (!isValidPhone) {
                    throw new BadRequestException( "Phone is not valid", request.getPhone());
                }
            }
            accountRegister.setPassword(passwordEncoder.encode(request.getPassword()));
            accountRegister.setEnable(true);

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER").get());
            accountRegister.setRoles(roles);

            accountRepository.save(accountRegister);
            return MapperUtil.map(accountRegister, AccountDtoToResponse.class);
        } else {
            throw new BadRequestException("Username or email is not valid", request.getUsername() + " or " + request.getEmail());
        }
    }
    @Override
    public MessageResponse updatePassword(CustomUserDetails customUserDetails, UpdatePasswordRequest updatePasswordRequest){
        Account currentAccount = accountRepository.findByEmail(customUserDetails.getEmail()).get();

        if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), currentAccount.getPassword())){
            throw new BadRequestException("No matching account found", updatePasswordRequest.getOldPassword());
        }
        if(updatePasswordRequest.getOldPassword().equals(updatePasswordRequest.getNewPassword())){
            throw new BadRequestException( "New password must different old password", updatePasswordRequest.getNewPassword());
        }
        String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        currentAccount.setPassword(newPassword);
        accountRepository.save(currentAccount);
        return new MessageResponse(200, "Update password successfully");
    }

    @Override
    public AccountDto getAccountById(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findById(customUserDetails.getId()).orElse(null);
        return MapperUtil.map(account, AccountDto.class);
    }

    @Override
    public AccountResponse getAllAccount(String name, int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> accounts = accountRepository.findByRoles_name(name, pageable);

        List<Account> accountList = accounts.getContent();
        List<AccountDto> content = accountList.stream().map(account -> MapperUtil.map(account, AccountDto.class)).collect(Collectors.toList());

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setContent(content);
        accountResponse.setPageNo(accounts.getNumber());
        accountResponse.setPageSize(accounts.getSize());
        accountResponse.setTotalElements(accounts.getTotalElements());
        accountResponse.setTotalPages(accounts.getTotalPages());
        accountResponse.setLast(accounts.isLast());

        return accountResponse;
    }
    @Override
    public AccountDtoToResponse updateProfile(CustomUserDetails customUserDetails, UpdateAccountRequest request){
        Account account = accountRepository.findByEmail(customUserDetails.getEmail()).get();

        account.setFullname(request.getFullname());

        if (!StringUtils.isEmpty(request.getPhone())) {
            boolean isValidPhone = phoneValidator.test(request.getPhone());
            if (!isValidPhone) {
                throw new BadRequestException("Phone is not valid: ", request.getPhone());
            } else {
                account.setPhone(request.getPhone());
            }
        }

        accountRepository.save(account);
        return MapperUtil.map(account, AccountDtoToResponse.class);
    }

    @Override
    public AccountDtoToResponse updateAccountByAdmin(UpdateAccountByAdminRequest request, long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setFullname(request.getFullname());
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        if (!StringUtils.isEmpty(request.getPhone())) {
            boolean isValidPhone = phoneValidator.test(request.getPhone());
            if (!isValidPhone) {
                throw new BadRequestException("Phone is not valid: ", request.getPhone());
            } else {
                account.setPhone(request.getPhone());
            }
        }
        account.setEnable(request.isEnable());
        accountRepository.save(account);
        return MapperUtil.map(account, AccountDtoToResponse.class);
    }

    @Override
    public void deleteAccountByAdmin(long id) {
        if(id <= 0){
            throw new InvalidIdException("Id must bigger than 1", id);
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        account.set_deleted(true);

        accountRepository.save(account);
    }


}
