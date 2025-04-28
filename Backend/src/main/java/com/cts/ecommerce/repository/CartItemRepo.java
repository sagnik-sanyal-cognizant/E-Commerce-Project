package com.cts.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.ecommerce.entity.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> 
{
	   // Custom JPQL query to find cart items by product name for a specific user.
       @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId AND LOWER(ci.product.name) LIKE LOWER(CONCAT('%', :productName, '%'))")
       List<CartItem> findCartItemsByProductName(@Param("userId") Long userId, @Param("productName") String productName);

}