package com.cts.ecommerce.repository;


import com.cts.ecommerce.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> 
{
       @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId AND LOWER(ci.product.name) LIKE LOWER(CONCAT('%', :productName, '%'))")
       List<CartItem> findCartItemsByProductName(@Param("userId") Long userId, @Param("productName") String productName);
}
