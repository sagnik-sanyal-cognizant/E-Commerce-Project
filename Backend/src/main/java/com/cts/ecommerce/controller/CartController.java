package com.cts.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.ecommerce.dto.CartDto;
import com.cts.ecommerce.dto.CartItemDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.CartService;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

	// CartService is injected to handle the business logic for cart operations
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Response> addToCart(@Valid @RequestParam Long productId) 
    {
    	
    	// Calls the cart service to add the product to the cart and returns the response
    	return ResponseEntity.ok(cartService.addToCart(productId));
    }

   @PutMapping("/update/add/{cartItemId}/{productId}")
    public ResponseEntity<Response> updateCartItemByQuantity(@Valid @PathVariable Long cartItemId,@PathVariable Long productId) 
    {
	    
        System.out.println("inside update"); // Logging...
        // Calls the cart service to update the cart item quantity and returns the response
        return ResponseEntity.ok(cartService.updateCartItemByQuantity(cartItemId,productId));
    }

    @PutMapping("update/remove/{cartItemId}/{productId}")
    public ResponseEntity<Response> removeCartItemByQuantity(@Valid @PathVariable Long cartItemId, @PathVariable Long productId) 
    {
    	
    	// Calls the cart service to update the cart item quantity and returns the response
        return ResponseEntity.ok(cartService.removeCartItemByQuantity(cartItemId,productId));
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Response> removeCartItem(@PathVariable Long cartItemId) 
    {
    	
    	// Calls the cart service to remove the cart item and returns the response
        return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
    }

    @GetMapping
    public ResponseEntity<CartDto> getCartByUser() 
    {
    	
    	// Calls the cart service to retrieve the cart for the current user and returns the response
        return ResponseEntity.ok(cartService.getCartByUser());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CartItemDto>> searchCartItemsByProductName(@RequestParam String productName) 
    {
    	
    	// Calls the cart service to search for cart items by product name and returns the response
        return ResponseEntity.ok(cartService.searchCartItemsByProductName(productName));
    }

}
