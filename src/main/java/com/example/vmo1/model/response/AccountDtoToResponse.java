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
    private Boolean enable;
    private Date created_at;
    private Date updated_at;
}
