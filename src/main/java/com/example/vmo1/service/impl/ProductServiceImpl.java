package com.example.vmo1.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.vmo1.commons.configs.MapperUtil;
import com.example.vmo1.commons.exceptions.BadRequestException;
import com.example.vmo1.commons.exceptions.InvalidIdException;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Image;
import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.repository.ImageRepository;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.repository.ShopRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ProductRequest save(ProductRequest metaData, MultipartFile[] files) {
        // convert dto to entity
        Product productRequest = MapperUtil.map(metaData, Product.class);

        List<Image> lstImg = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
//                BufferedImage bi = ImageIO.read(file.getInputStream());
                Map result = upload(file);

                Image dbImage = new Image();
                dbImage.setFileName((String)result.get("original_filename"));
                dbImage.setFileType(file.getContentType());
                dbImage.setImageUrl((String)result.get("url"));
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

        // convert entity to dto
        return MapperUtil.map(product, ProductRequest.class);

    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        return result;
    }
    @Override
    public ProductRequest updateProduct(ProductRequest metaData, long id, MultipartFile[] files) {
        Product product = MapperUtil.map(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Find product by id", "Product", id)), Product.class);
        imageRepository.deleteImagesByProduct(product.getId());

        return save(metaData, files);
    }
    @Override
    public ProductResponse getAllProduct(CustomUserDetails customUserDetails, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Product> products = productRepository.findAllByShopId(pageable, customUserDetails.getId());

        List<Product> productList = products.getContent();
        List<ProductRequest> content = productList.stream().map(product -> MapperUtil.map(product, ProductRequest.class)).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
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
        if(id <= 0){
            throw new InvalidIdException("Id must bigger than 1", id);
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Find product by id", "Product", id));
        product.set_deleted(true);
        productRepository.save(product);
    }



}

