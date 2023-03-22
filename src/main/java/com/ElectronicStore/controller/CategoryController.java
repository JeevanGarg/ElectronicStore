package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("category")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        return new ResponseEntity<>(this.categoryService.create(categoryDto), HttpStatus.OK);
    }

    //update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") String categoryId,
                               @Valid @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.categoryService.update(categoryDto,categoryId),HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId) throws ResourceNotFoundException {
        this.categoryService.delete(categoryId);
        ApiResponseMessage message=ApiResponseMessage.builder()
                .message("Successfully deleted")
                .success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping("/allCategories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir)
    {
        return new ResponseEntity<>(this.categoryService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") String categoryId) throws ResourceNotFoundException
    {
        return new ResponseEntity<>(categoryService.get(categoryId),HttpStatus.OK);
    }

}
