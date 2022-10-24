package com.example.vmo1.service.impl;

import com.example.vmo1.commons.configs.MapperUtil;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.OrderDetail;
import com.example.vmo1.model.entity.Orders;
import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.request.OrdersRequest;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.OrderDtoToResponse;
import com.example.vmo1.model.response.OrderResponse;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.OrderDetailRepository;
import com.example.vmo1.repository.OrdersRepository;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public MessageResponse addOrders(OrdersRequest metaData, CustomUserDetails customUserDetails) {
        Orders ordersRequest = MapperUtil.map(metaData, Orders.class);
        Account account = accountRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", customUserDetails.getId()));
        ordersRequest.setAccount(account);
        ordersRequest.setOrderAt(LocalDateTime.now());
        Orders orders = ordersRepository.save(ordersRequest);

        int total = 0;
        for (OrderDetail orDe: ordersRequest.getLstOrderDetail()) {
            Product product = productRepository.findById(orDe.getProduct().getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product", "id", orDe.getProduct().getId()));
            orDe.setPrice(product.getPrice());
            orDe.setTotal((double) (product.getPrice() * orDe.getQuantity()));
            total += orDe.getTotal();

            orDe.setOrders(orders);
            orderDetailRepository.save(orDe);
        }


        return new MessageResponse(200, "successfully");
    }

    @Override
    public MessageResponse updateOrders(OrdersRequest metaData, CustomUserDetails customUserDetails, Long id) {
        Orders findOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        Orders ordersRequest = MapperUtil.map(metaData, Orders.class);

        Account account = accountRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", customUserDetails.getId()));
        ordersRequest.setAccount(account);
        ordersRequest.setOrderAt(LocalDateTime.now());
        ordersRequest.setNote(metaData.getNote());
        Orders orders = ordersRepository.save(ordersRequest);

        int total = 0;
        for (OrderDetail orDe: ordersRequest.getLstOrderDetail()) {
            Product product = productRepository.findById(orDe.getProduct().getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product", "id", orDe.getProduct().getId()));
            orDe.setPrice(product.getPrice());
            orDe.setTotal((double) (product.getPrice() * orDe.getQuantity()));
            total += orDe.getTotal();

            orDe.setOrders(orders);
            orderDetailRepository.save(orDe);
        }


        return new MessageResponse(200, "successfully");

    }

    @Override
    public OrderDtoToResponse findById(Long id) {
        return MapperUtil.map(ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orders", "id", id)), OrderDtoToResponse.class);

    }

    @Override
    public OrderResponse findAll(CustomUserDetails customUserDetails, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Orders> orders = ordersRepository.findAllByAccountId(pageable, customUserDetails.getId());

        List<Orders> ordersList = orders.getContent();
        List<OrderDtoToResponse> content = ordersList.stream().map(order -> MapperUtil.map(order, OrderDtoToResponse.class)).collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());

        return orderResponse;
    }
}
