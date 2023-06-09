package com.ElectronicStore.dtos;


import com.ElectronicStore.entities.Category;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto
{
    private String productId;

    private String title;

    private String description;

    private Integer price;

    private Integer discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;

    private String productImageName;

    private CategoryDto category;
}
