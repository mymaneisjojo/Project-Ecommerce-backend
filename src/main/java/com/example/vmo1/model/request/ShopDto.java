package com.example.vmo1.model.request;

import com.example.vmo1.model.response.AccountDtoToResponse;
import com.example.vmo1.model.response.ProductDtoToResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    @NotNull(message = "Name is required")
    private String name;

    private String banner;
    @NotNull(message = "Address is required")
    private String address;
    @NotNull
    private AccountDtoToResponse account;

    private List<ProductDtoToResponse> lstPro;
}
