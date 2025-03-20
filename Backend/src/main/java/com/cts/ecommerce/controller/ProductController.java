package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.exception.InvalidCredentialsException;
import com.cts.ecommerce.service.interf.ProductService;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	// ProductService is injected to handle the business logic for product operations
    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestParam @NotNull Long categoryId,
            @RequestParam @NotNull MultipartFile image,
            @RequestParam @NotNull String name,
            @RequestParam @NotNull Double discount,
            @RequestParam @NotNull Integer quantity,
            @RequestParam @NotNull String description,
            @RequestParam @NotNull BigDecimal price
    ) {
    	// Validates the input parameters and throws an exception if any required field is missing
        if (categoryId == null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null) {
            throw new InvalidCredentialsException("All Fields are Required");
        }
        // Calls the product service to create the product and returns the response
        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, discount, quantity,description, price));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam @NotNull Long productId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price
    ) {
    	// Calls the product service to update the product and returns the response
        return ResponseEntity.ok(productService.updateProduct(productId, categoryId, image, name, description, price));
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
        
    	// Calls the product service to delete the product and returns the response
    	return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("/get-by-product-id/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
        
    	// Calls the product service to retrieve the product by its ID and returns the response
    	return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllProducts() {
        
    	// Calls the product service to retrieve all products and returns the response
    	return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-by-category-id/{categoryId}")
    public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId) {
        
    	// Calls the product service to retrieve products by their category ID and returns the response
    	return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchForProduct(@RequestParam String searchValue) {
        
    	// Calls the product service to search for products by the search value and returns the response
    	return ResponseEntity.ok(productService.searchProduct(searchValue));
    }
}