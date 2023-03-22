package com.ElectronicStore.services.impl;

import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.entities.Category;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.helper.Helper;
import com.ElectronicStore.repository.CategoryRepository;
import com.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String categoryId= UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category=modelMapper.map(categoryDto, Category.class);
        Category savedCategory=categoryRepository.save(category);
        CategoryDto categoryDto1=modelMapper.map(savedCategory, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) throws ResourceNotFoundException
    {
        Optional<Category> categoryOptional=this.categoryRepository.findById(categoryId);
        Category category=categoryOptional.get();
        if(categoryOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Category Id not found!");
        }

        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category savedCategory=this.categoryRepository.save(category);
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
    public PageableResponse<CategoryDto> getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page=this.categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse=Helper.getPageableResponse(page,CategoryDto.class);

        return pageableResponse;
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
