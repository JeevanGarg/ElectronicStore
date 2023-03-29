package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable("productId") String productId) throws ResourceNotFoundException {
        ProductDto update = productService.update(productDto, productId);

        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId) throws ResourceNotFoundException {
        productService.delete(productId);
       ApiResponseMessage message= ApiResponseMessage.builder().message("Product is deleted successfully")
                .success(true).status(HttpStatus.OK).build();

       return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") String productId) throws ResourceNotFoundException {
        ProductDto productDto = productService.get(productId);

        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageableResponse> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse> searchProduct(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
            @PathVariable("query") String query
    )
    {
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

}
