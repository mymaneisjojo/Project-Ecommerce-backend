package com.example.vmo1.controller;

import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.repository.ImageRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.ImageService;
import com.example.vmo1.service.ProductService;
import com.example.vmo1.validation.annotation.CurrentUser;
import com.example.vmo1.validation.annotation.ValidFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<?> uploadMultipleFiles(@ValidFile @RequestPart("files") MultipartFile[] files, @Valid @RequestPart ProductRequest metaData) {
        return ResponseEntity.ok(productService.save(metaData, files));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestPart ProductRequest metaData, @PathVariable(name = "id") long id, MultipartFile[] files) {
        ProductRequest productRequest = productService.updateProduct(metaData, id, files);
        return new ResponseEntity<>(productRequest, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ProductResponse getAllProducts(@CurrentUser CustomUserDetails customUserDetails, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "1", required = false) int pageSize) {
        return productService.getAllProduct(customUserDetails, pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new MessageResponse(200, "Product have been delete!!!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
}
