package com.example.vmo1.service.impl;

import com.example.vmo1.commons.configs.MapperUtil;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Image;
import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.repository.ImageRepository;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductRequest save(ProductRequest productResquest, MultipartFile[] files) {
        // convert dto to entity
        Product productRequest = MapperUtil.map(productResquest, Product.class);

        List<Image> lstImg = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String name = file.getOriginalFilename();

                // random name generate file
                String randomID = UUID.randomUUID().toString();
                String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));
                String filePath = path + File.separator + filename1;

                File f = new File(path);
                if (!f.exists()) {
                    f.mkdir();
                }
                Files.copy(file.getInputStream(), Paths.get(filePath));
                Image dbImage = new Image();
                dbImage.setFileName(name);
                dbImage.setProduct(productRequest);
                lstImg.add(dbImage);

            } catch (IOException e) {
                e.printStackTrace();
//                return new UploadFileResponse(null, "Image is not upload success");
            }
        }
        Product product = productRepository.save(productRequest);
        imageRepository.saveAll(lstImg);
        product.setLstImg(lstImg);
//        product.setShop(productDto.getShop());

        // convert entity to dto
        return MapperUtil.map(product, ProductRequest.class);

    }
    @Override
    public ProductRequest updateProduct(ProductRequest productRequest, long id, MultipartFile[] files) {
        Product product = MapperUtil.map(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Find product by id", "Product", id)), Product.class);
        imageRepository.deleteImagesByProduct(product.getId());

        return save(productRequest, files);
    }
    @Override
    public com.example.vmo1.model.response.ProductResponse getAllProduct(CustomUserDetails customUserDetails, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Product> products = productRepository.findAllByShopId(pageable, customUserDetails.getId());

        List<Product> productList = products.getContent();
        List<ProductRequest> content = productList.stream().map(product -> MapperUtil.map(product, ProductRequest.class)).collect(Collectors.toList());

        com.example.vmo1.model.response.ProductResponse productResponse = new com.example.vmo1.model.response.ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    @Override
    public Product findById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Find product by id", id));
        return product;
    }

    @Override
    public void deleteProduct(long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Find product by id", "Product", id));
        product.setIs_deleted(true);
        productRepository.save(product);
    }

    @Override
    public int countProductsByShopId(long shop_id) {
        return productRepository.countProductsByShopId(shop_id);
    }

}

