package com.example.vmo1.model.request;

import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.response.ProductDtoToResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDto {
    private Long id;
    private ProductDtoToResponse product;
    private Double price;
    private Double quantity;
    private Double total;
}
