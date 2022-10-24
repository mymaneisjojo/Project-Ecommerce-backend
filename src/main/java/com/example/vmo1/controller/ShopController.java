package com.example.vmo1.controller;

import com.example.vmo1.model.entity.Shop;
import com.example.vmo1.model.request.ShopDto;
import com.example.vmo1.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestPart MultipartFile file,@Valid @RequestPart ShopDto metaData) throws IOException {
        return ResponseEntity.ok(shopService.add(metaData, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestPart ShopDto metaData, @PathVariable("id") long id, @RequestPart MultipartFile file) throws IOException{
        ShopDto shopResponse = shopService.update(metaData, id, file);
        return new ResponseEntity<>(shopResponse, HttpStatus.OK);
    }

}
