package com.example.vmo1.model.request;

import com.example.vmo1.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @Id
    private long id;
    @NotNull
    private String name;
    @NotNull
    private float price;
    @NotNull
    private int quantity;
    private Category category;
    private List<ImageDto> lstImg;
    private Shop shop;
    private List<Color> colors;
    private List<Size> sizes;
}
