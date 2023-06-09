package com.ElectronicStore.services.impl;

import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.entities.Category;
import com.ElectronicStore.entities.Product;
import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.helper.Helper;
import com.ElectronicStore.repository.CategoryRepository;
import com.ElectronicStore.repository.ProductRepository;
import com.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto)
    {
        String id= UUID.randomUUID().toString();
        Product product=this.modelMapper.map(productDto, Product.class);
        product.setProductId(id);
        product.setAddedDate(new Date());
        Product save = productRepository.save(product);
        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) throws ResourceNotFoundException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if(productOptional.isEmpty())
        {
            throw new ResourceNotFoundException("product not found with given Id");
        }

            Product product=productOptional.get();


            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setStock(productDto.getStock());
            product.setQuantity(productDto.getQuantity());
            product.setTitle(productDto.getTitle());
            product.setDiscountedPrice(productDto.getDiscountedPrice());
            product.setLive(productDto.getLive());
            product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);
        return this.modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) throws ResourceNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Product not found by given Id");
        }

        productRepository.deleteById(productId);

    }

    @Override
    public ProductDto get(String productId) throws ResourceNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Product not found by given Id");
        }

        return modelMapper.map(productOptional.get(),ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(title,pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public ProductDto createWithcategory(ProductDto productDto, String categoryId) throws ResourceNotFoundException {
        //fetch the category from db
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        String id= UUID.randomUUID().toString();
        Product product=this.modelMapper.map(productDto, Product.class);
        product.setProductId(id);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product save = productRepository.save(product);
        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setCategory(category);
        Product save = productRepository.save(product);

        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) throws ResourceNotFoundException {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Page<Product> page=productRepository.findByCategory(category,pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
