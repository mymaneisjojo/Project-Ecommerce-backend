package com.example.vmo1.controller;


import com.example.vmo1.model.request.SignupRequest;
import com.example.vmo1.model.request.UpdateAccountByAdminRequest;
import com.example.vmo1.model.response.AccountDtoToResponse;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.ShopResponse;
import com.example.vmo1.service.AccountService;
import com.example.vmo1.service.ProductService;
import com.example.vmo1.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccountByAdmin(@Valid @RequestBody SignupRequest request){
        return ResponseEntity.ok(accountService.addAccountByAdmin(request));
    }

    @GetMapping("/account/all")
    public ResponseEntity<?> getAllAccount(@RequestParam(defaultValue = "ROLE_USER") String name, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
        return ResponseEntity.ok(accountService.getAllAccount(name, pageNo, pageSize));
    }

    @GetMapping("/shop/all")
    public ShopResponse getAllShop(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize){
        return shopService.getAllShop(pageNo, pageSize);
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        return ResponseEntity.ok(shopService.statistProduct());
    }

    @PutMapping("/account/update/{id}")
    public ResponseEntity<?> updateAccountByAdmin(@Valid @RequestBody UpdateAccountByAdminRequest request, @PathVariable("id") long id){
        AccountDtoToResponse accountResponse = accountService.updateAccountByAdmin(request, id);
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<?> deleteAccountByAdmin(@PathVariable("id") long id){
        accountService.deleteAccountByAdmin(id);
        return ResponseEntity.ok(new MessageResponse(200,"Account have been delete!!!"));
    }


}
