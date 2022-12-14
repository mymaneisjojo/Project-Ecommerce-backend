package com.example.vmo1.model.response;

import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.request.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountProduct {
    private String name;
    private String accountName;
    private String accountEmail;
    private  int totalProduct;
}
