package com.example.vmo1.service;

import com.example.vmo1.model.request.ShopDto;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.ShopDtoToResponse;
import com.example.vmo1.model.response.ShopResponse;
import com.example.vmo1.model.response.StatisticResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ShopService {
    ShopDtoToResponse add(ShopDto metaData, MultipartFile file) throws IOException;
    ShopDto update(ShopDto metaData, long id, MultipartFile file);
    ShopResponse getAllShop(int pageNo, int pageSize);

    StatisticResponse statistProduct();

}
