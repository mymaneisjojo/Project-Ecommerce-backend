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
public class ProductRequest {
    @Id
    private long id;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    private float price;
    @NotNull(message = "Quantity is required")
    private int quantity;
    private Boolean is_deleted;
    private Category category;
    private List<ImageDto> lstImg;
    private Shop shop;
    private List<Color> colors;
    private List<Size> sizes;
}
