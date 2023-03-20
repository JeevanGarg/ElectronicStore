package com.ElectronicStore.services.impl;

import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.entities.Category;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.repository.CategoryRepository;
import com.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto)
    {
        Category category=modelMapper.map(categoryDto, Category.class);
        Category savedCategory=categoryRepository.save(category);
        CategoryDto categoryDto1=modelMapper.map(savedCategory, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) throws ResourceNotFoundException
    {
        Optional<Category> categoryOptional=this.categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Category Id not found!");
        }

        Category savedCategory=this.categoryRepository.save(categoryOptional.get());
        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) throws ResourceNotFoundException
    {
        Optional<Category> deleteOptional=this.categoryRepository.findById(categoryId);

        if(deleteOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Category Id not found!");
        }
        this.categoryRepository.deleteById(categoryId);

    }

    @Override
    public PageableResponse<CategoryDto> getAll()
    {
        return null;
    }

    @Override
    public CategoryDto get(String categoryId) throws ResourceNotFoundException
    {
        Optional<Category> categoryOptional=this.categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Category Id is not found!");
        }
        return this.modelMapper.map(categoryOptional.get(),CategoryDto.class);
    }
}
