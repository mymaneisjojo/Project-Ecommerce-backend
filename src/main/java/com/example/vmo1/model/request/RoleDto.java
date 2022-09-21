package com.example.vmo1.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class RoleDto {
    private Long id;
    private String name;
    private Boolean is_deleted;
//    private List<AccountDto> account;
}
