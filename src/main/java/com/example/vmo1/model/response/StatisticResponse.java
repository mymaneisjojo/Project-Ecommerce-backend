package com.example.vmo1.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    private int totalShop;
    private List<CountProduct> lstTotalProduct;
}
