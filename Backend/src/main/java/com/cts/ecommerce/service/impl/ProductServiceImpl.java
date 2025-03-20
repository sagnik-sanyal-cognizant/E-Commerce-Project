package com.cts.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.ecommerce.dto.ProductDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.entity.Category;
import com.cts.ecommerce.entity.Product;
import com.cts.ecommerce.exception.NotFoundException;
import com.cts.ecommerce.mapper.EntityDtoMapper;
import com.cts.ecommerce.repository.CategoryRepo;
import com.cts.ecommerce.repository.ProductRepo;
import com.cts.ecommerce.service.interf.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    // Creates a new product
    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name,Double discount,Integer quantity,String description, BigDecimal price) {
        
    	// Retrieves the category by ID and throws NotFoundException if not found
    	Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDiscount(discount);
        product.setQuantity(quantity);
        product.setDescription(description);
        try {
            product.setImageUrl(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image data", e);
        }

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();
    }

    // Updates an existing product
    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        
    	// Retrieves the product by ID and throws NotFoundException if not found
    	Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));

        Category category = null;
        if (categoryId != null) {
            category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        }

        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);
        if (image != null && !image.isEmpty()) {
            try {
                product.setImageUrl(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image data", e);
            }
        }

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    // Deletes a product by its ID
    @Override
    public Response deleteProduct(Long productId) {
        
    	// Retrieves the product by ID and throws NotFoundException if not found
    	Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productRepo.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }
    
    // Retrieves a product by its ID
    @Override
    public Response getProductById(Long productId) {
    	
    	// Retrieves the product by ID and throws NotFoundException if not found
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }
    
    // Retrieves all products
    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    // Retrieves products by their category ID
    @Override
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        // Checks if the products list is empty and throws NotFoundException if no products are found
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    // Searches for products by their name or description
    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
        
        // Checks if the products list is empty and throws NotFoundException if no products are found
        if (products.isEmpty()) {
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }
}