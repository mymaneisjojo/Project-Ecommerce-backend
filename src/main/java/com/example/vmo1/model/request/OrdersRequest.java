package com.example.vmo1.model.request;

import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@Data
public class OrdersRequest {
    private LocalDateTime orderAt;
    private String note;
    private AccountDto account;
    private List<OrderDetailDto> lstOrderDetail;
}
