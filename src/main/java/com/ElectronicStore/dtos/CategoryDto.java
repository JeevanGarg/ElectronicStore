package com.ElectronicStore.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto
{
    private String categoryId;
    @NotBlank
    @Size(min = 1,message = "title must be minimum 1 string!!")
    private String title;
    @NotBlank(message = "Description required!")
    private String description;

    private String coverImage;
}
