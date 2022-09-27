package com.example.vmo1.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoToResponse {
    @Id
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private boolean enable;
    private boolean is_deleted;
    private Date created_at;
    private Date updated_at;

    public AccountDtoToResponse(String username, String fullname, String email, String phone, boolean enable, boolean is_deleted, Date created_at, Date updated_at) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.enable = enable;
        this.is_deleted = is_deleted;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
