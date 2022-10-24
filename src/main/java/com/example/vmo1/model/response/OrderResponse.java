package com.example.vmo1.model.response;

import com.example.vmo1.model.request.ProductRequest;
import lombok.Data;

import java.util.List;
@Data
public class OrderResponse {
    private List<OrderDtoToResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
