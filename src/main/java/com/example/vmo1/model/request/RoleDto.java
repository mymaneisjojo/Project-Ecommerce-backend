package com.example.vmo1.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private Boolean is_deleted;
//    private List<AccountDto> account;
}
