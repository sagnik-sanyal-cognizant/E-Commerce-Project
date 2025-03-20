package com.cts.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.cts.ecommerce.dto.CategoryDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.entity.Category;
import com.cts.ecommerce.exception.NotFoundException;
import com.cts.ecommerce.mapper.EntityDtoMapper;
import com.cts.ecommerce.repository.CategoryRepo;
import com.cts.ecommerce.service.interf.CategoryService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    // Creates a new category
    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        
    	Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }
    
    // Updates an existing category
    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
    	// Retrieves the category by ID and throws NotFoundException if not found
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("category updated successfully")
                .build();
    }

    // Retrieves all categories
    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .collect(Collectors.toList());

        return  Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }

    // Retrieves a category by its ID
    @Override
    public Response getCategoryById(Long categoryId) {
    	
    	// Retrieves the category by ID and throws NotFoundException if not found
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    // Deletes a category by its ID
    @Override
    public Response deleteCategory(Long categoryId) {
    	
    	// Retrieves the category by ID and throws NotFoundException if not found
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        categoryRepo.delete(category);
        return Response.builder()
                .status(200)
                .message("Category was deleted successfully")
                .build();
    }
}
