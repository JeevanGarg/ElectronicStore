package com.ElectronicStore.services;

import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProductService
{
    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto,String productId) throws ResourceNotFoundException;

    void delete(String productId) throws ResourceNotFoundException;

    ProductDto get(String productId) throws ResourceNotFoundException;

    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    PageableResponse<ProductDto> searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir);

    //create Product with category
    ProductDto createWithcategory(ProductDto productDto,String categoryId) throws ResourceNotFoundException;

    ProductDto updateCategory(String productId,String categoryId) throws ResourceNotFoundException;

    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) throws ResourceNotFoundException;
}
