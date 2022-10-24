package com.example.vmo1.service;

import com.example.vmo1.model.request.OrdersRequest;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.OrderDtoToResponse;
import com.example.vmo1.model.response.OrderResponse;
import com.example.vmo1.security.service.CustomUserDetails;

public interface OrdersService {
    MessageResponse addOrders(OrdersRequest metaData, CustomUserDetails customUserDetails);
    MessageResponse updateOrders(OrdersRequest metaData, CustomUserDetails customUserDetails, Long id);
    OrderDtoToResponse findById(Long id);

    OrderResponse findAll(CustomUserDetails customUserDetails, int pageNo, int pageSize);
}
