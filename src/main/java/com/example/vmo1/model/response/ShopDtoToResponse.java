package com.example.vmo1.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDtoToResponse {
    private String name;
    private String banner;
    private String address;
    private AccountDtoToResponse account;
    private Date created_at;
    private boolean is_deleted;
}
