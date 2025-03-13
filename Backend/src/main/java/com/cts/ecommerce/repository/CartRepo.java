package com.cts.ecommerce.repository;


import com.cts.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> 
{
    Optional<Cart> findByUserId(Long userId);
}