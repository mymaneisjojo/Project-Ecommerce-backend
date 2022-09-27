package com.example.vmo1.service;

import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.security.service.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductRequest save(ProductRequest metaData, MultipartFile[] files);
    ProductRequest updateProduct(ProductRequest metaData, long id, MultipartFile[] files);
    ProductResponse getAllProduct(CustomUserDetails customUserDetails, int pageNo, int pageSize);

    Product findById(long id);
    void deleteProduct(long id);

}
