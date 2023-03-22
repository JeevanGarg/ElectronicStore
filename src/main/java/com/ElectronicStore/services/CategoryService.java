package com.ElectronicStore.services;

import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.exceptions.ResourceNotFoundException;

public interface CategoryService
{
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId) throws ResourceNotFoundException;

    //delete
    void delete(String categoryId) throws ResourceNotFoundException;

    //get all
    PageableResponse<CategoryDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get single category detail
    CategoryDto get(String categoryId) throws ResourceNotFoundException;

    //search
}
