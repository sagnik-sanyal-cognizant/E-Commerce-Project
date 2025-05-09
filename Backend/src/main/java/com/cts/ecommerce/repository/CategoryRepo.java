package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
