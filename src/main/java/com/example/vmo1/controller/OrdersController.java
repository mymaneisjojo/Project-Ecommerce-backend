package com.example.vmo1.controller;

import com.example.vmo1.model.request.OrdersRequest;
import com.example.vmo1.model.response.OrderResponse;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.OrdersService;
import com.example.vmo1.validation.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody OrdersRequest metaData, @CurrentUser CustomUserDetails customUserDetails){
        return ResponseEntity.ok(ordersService.addOrders(metaData, customUserDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(ordersService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody OrdersRequest metaData, @CurrentUser CustomUserDetails customUserDetails,@PathVariable("id") Long id){
        return ResponseEntity.ok(ordersService.updateOrders(metaData, customUserDetails, id));
    }

    @GetMapping("/all")
    public OrderResponse getAllOrders(@CurrentUser CustomUserDetails customUserDetails, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        return ordersService.findAll(customUserDetails, pageNo, pageSize);
    }
}
