package com.ElectronicStore.controller;

import com.ElectronicStore.dtos.*;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.FileService;
import com.ElectronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Value("${product.profile.image.path}")
    private String imagePath;

    @Autowired
    private FileService fileService;

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

    //upload image

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable("productId") String productId,
            @RequestParam("productImage") MultipartFile file
            ) throws ResourceNotFoundException, IOException
    {
        String fileName = fileService.uploadFile(file, imagePath);

        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);

        ProductDto updateProduct = productService.update(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(updateProduct.getProductImageName()).message("Image successfully uploaded")
                .success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    @GetMapping("/image/{productId}")
    public void serveProductImage(
            @PathVariable("productId") String productId,
            HttpServletResponse response) throws ResourceNotFoundException, IOException
    {
        ProductDto productDto = productService.get(productId);
        InputStream inputStream = fileService.getResource(imagePath, productDto.getProductImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }

    //create product with category

    @PostMapping("/{categoryId}")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto) throws ResourceNotFoundException
    {
        ProductDto productWithcategory = productService.createWithcategory(productDto, categoryId);

        return new ResponseEntity<>(productWithcategory,HttpStatus.CREATED);
    }

    //update category in product
    @PutMapping("/{productId}/{categoryId}")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId) throws ResourceNotFoundException
    {
        ProductDto productWithcategory = productService.updateCategory(productId, categoryId);

        return new ResponseEntity<>(productWithcategory,HttpStatus.OK);
    }


    //get all products from categories

    @GetMapping("/allProducts/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) throws ResourceNotFoundException
    {
        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
