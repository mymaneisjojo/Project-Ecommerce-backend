package com.example.vmo1.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.vmo1.commons.configs.MapperUtil;
import com.example.vmo1.commons.exceptions.ResourceAlreadyInUseException;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Shop;
import com.example.vmo1.model.request.ShopDto;
import com.example.vmo1.model.response.*;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.repository.ShopRepository;
import com.example.vmo1.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ShopDtoToResponse add(ShopDto metaData, MultipartFile file) throws IOException {
        Shop shop = MapperUtil.map(metaData, Shop.class);
        // if metaDta = null
        //
        boolean isAccountExist = shopRepository.findByAccountId(shop.getAccount().getId()).isPresent();
        if(isAccountExist){
            throw new ResourceAlreadyInUseException("Account","name", shop.getAccount().getUsername());
        }
        Map result = upload(file);
            // save image to field banner in shop
        shop.setBanner((String)result.get("url"));
         shopRepository.save(shop);

        return MapperUtil.map(shop, ShopDtoToResponse.class);
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
    public ShopDto update(ShopDto metaData, long id, MultipartFile file) {
        Shop shop = MapperUtil.map(shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop","id", id)), Shop.class);

        return MapperUtil.map(shopRepository.save(shop), ShopDto.class);
    }

    @Override
    public ShopResponse getAllShop(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Shop> shops = shopRepository.findAll(pageable);
        List<Shop> shopList = shops.getContent();
        List<ShopDto> content = shopList.stream().map(shop -> MapperUtil.map(shop, ShopDto.class)).collect(Collectors.toList());
        ShopResponse shopResponse = new ShopResponse();
        shopResponse.setContent(content);
        shopResponse.setPageNo(shops.getNumber());
        shopResponse.setPageSize(shops.getSize());
        shopResponse.setTotalElements(shops.getTotalElements());
        shopResponse.setTotalPages(shops.getTotalPages());
        shopResponse.setLast(shops.isLast());
        return shopResponse;
    }

    @Override
    public StatisticResponse statistProduct() {
        int totalShop = shopRepository.countShops();
        List<Shop> lstShop = shopRepository.findAll();
        List<CountProduct> lstCountProduct = new ArrayList<>();
        int totalProduct = 0;
        for (Shop lst: lstShop) {
            totalProduct =  productRepository.countProductsByShopId(lst.getId());
            CountProduct countProduct = new CountProduct();
            countProduct.setTotalProduct(totalProduct);
            countProduct.setName(lst.getName());
            countProduct.setAccountName(lst.getAccount().getUsername());
            countProduct.setAccountEmail(lst.getAccount().getEmail());
            lstCountProduct.add(countProduct);
        }

        StatisticResponse statisticResponse = new StatisticResponse();
        statisticResponse.setTotalShop(totalShop);
        statisticResponse.setLstTotalProduct(lstCountProduct);
        return statisticResponse;
    }

}
