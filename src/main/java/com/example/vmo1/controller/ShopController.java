package com.example.vmo1.controller;

import com.example.vmo1.model.request.ShopDto;
import com.example.vmo1.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestPart MultipartFile file,@Valid @RequestPart ShopDto metaData) {
        return ResponseEntity.ok(shopService.add(metaData, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestPart ShopDto shopDto, @PathVariable("id") long id, @RequestPart MultipartFile file){
        ShopDto shopResponse = shopService.update(shopDto, id, file);
        return new ResponseEntity<>(shopResponse, HttpStatus.OK);
    }

//    @GetMapping("/count")
//    public ResponseEntity<?> count(){
//        return ResponseEntity.ok(shopService.statistProduct());
//    }
}
