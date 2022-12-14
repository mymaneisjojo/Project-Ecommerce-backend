package com.example.vmo1.model.request;

import com.example.vmo1.model.entity.Role;
import com.example.vmo1.model.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @Id
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private boolean enable;
    private Date created_at;
    private Date updated_at;
    private List<RoleDto> roles;

    public AccountDto(Long id, String username, String email, boolean enable, List<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enable = enable;
        this.roles = roles;
    }
}
