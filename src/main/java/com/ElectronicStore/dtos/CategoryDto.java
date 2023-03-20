package com.ElectronicStore.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto
{
    private String categoryId;
    @NotBlank
    @Min(value = 4,message = "title must be minimum 4 character!!")
    private String title;
    @NotBlank(message = "Description required!")
    private String description;

    private String coverImage;
}
