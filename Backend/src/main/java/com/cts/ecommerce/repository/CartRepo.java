package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.entity.Cart;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> 
{
    Optional<Cart> findByUserId(Long userId);
}
