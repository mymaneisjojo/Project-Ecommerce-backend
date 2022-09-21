package com.example.vmo1.service;

import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.security.service.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductRequest save(ProductRequest productRequest, MultipartFile[] files);
    ProductRequest updateProduct(ProductRequest productRequest, long id, MultipartFile[] files);
    com.example.vmo1.model.response.ProductResponse getAllProduct(CustomUserDetails customUserDetails, int pageNo, int pageSize);

    Product findById(long id);
    void deleteProduct(long id);

}
