package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.CategoryDto;
import com.ElectronicStore.dtos.ImageResponse;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.CategoryService;
import com.ElectronicStore.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("category")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

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
    @DeleteMapping("/delete/{categoryId}")
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


    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam("categoryImage") MultipartFile file,
            @PathVariable("categoryId") String categoryId) throws ResourceNotFoundException, IOException
    {
        String fileName = fileService.uploadFile(file, imageUploadPath);
        CategoryDto categoryDto = categoryService.get(categoryId);

        categoryDto.setCoverImage(fileName);
        categoryService.update(categoryDto,categoryId);

        ImageResponse response=ImageResponse.builder().imageName(fileName).message("successfully uploaded")
                .success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }


    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(
            @PathVariable("categoryId") String categoryId,
            HttpServletResponse response) throws ResourceNotFoundException, IOException {
        CategoryDto categoryDto = categoryService.get(categoryId);
        InputStream inputStream = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
