package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.ecommerce.dto.CategoryDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	// CategoryService is injected to handle the business logic for category operations
	private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        
    	// Calls the category service to create the category and returns the response
    	return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategories(){
        
    	// Calls the category service to retrieve all categories and returns the response
    	return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        
    	// Calls the category service to update the category and returns the response
    	return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId){
        
    	// Calls the category service to delete the category and returns the response
    	return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId){
        
    	// Calls the category service to retrieve the category by its ID and returns the response
    	return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

}
