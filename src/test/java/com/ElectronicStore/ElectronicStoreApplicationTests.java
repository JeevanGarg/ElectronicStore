package com.ElectronicStore;


import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class  ElectronicStoreApplicationTests
{
    @Autowired
    private CategoryService categoryService;

    @Test
    public void getCategoryTest() throws ResourceNotFoundException {
        CategoryDto categoryDto=this.categoryService.get("bc73f1bd-6819-4be6-957b-ce819666fc8e");

        Assertions.assertEquals("Electronics",categoryDto.getTitle());
    }

}


