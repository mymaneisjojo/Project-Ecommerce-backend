package com.example.vmo1.model.response;

import com.example.vmo1.model.entity.Category;
import com.example.vmo1.model.entity.Color;
import com.example.vmo1.model.entity.Shop;
import com.example.vmo1.model.entity.Size;
import com.example.vmo1.model.request.ImageDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class ProductDtoToResponse {
    private long id;
    private String name;
    private float price;
    private int quantity;
    private Boolean is_deleted;
    private Category category;
    private List<ImageDto> lstImg;
    private List<Color> colors;
    private List<Size> sizes;
}
