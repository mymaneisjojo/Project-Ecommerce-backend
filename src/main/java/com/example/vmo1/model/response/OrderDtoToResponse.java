package com.example.vmo1.model.response;

import com.example.vmo1.model.request.OrderDetailDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDtoToResponse {
    private Long id;
    private String note;
    private LocalDateTime orderAt;
    private AccountDtoToResponse account;
    private List<OrderDetailDto> lstOrderDetail;
}
