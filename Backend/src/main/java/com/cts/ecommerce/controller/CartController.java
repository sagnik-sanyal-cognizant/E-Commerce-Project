package com.cts.ecommerce.controller;


import com.cts.ecommerce.dto.CartDto;
import com.cts.ecommerce.dto.CartItemDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.repository.CartRepo;
import com.cts.ecommerce.service.interf.CartService;
import com.cts.ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("/add")
    public ResponseEntity<Response> addToCart(@RequestParam Long productId) 
    {
        return ResponseEntity.ok(cartService.addToCart(productId));
    }

   @PutMapping("/update/add/{cartItemId}/{productId}")
    public ResponseEntity<Response> updateCartItemByQuantity(@PathVariable Long cartItemId,@PathVariable Long productId) 
    {
        // log.info("Update request received for cartItemId: {}, productId: {}", cartItemId, productId);
        System.out.println("inside update");
        return ResponseEntity.ok(cartService.updateCartItemByQuantity(cartItemId,productId));
    }

    @PutMapping("update/remove/{cartItemId}/{productId}")
    public ResponseEntity<Response> removeCartItemByQuantity(@PathVariable Long cartItemId, @PathVariable Long productId) 
    {
        return ResponseEntity.ok(cartService.removeCartItemByQuantity(cartItemId,productId));
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Response> removeCartItem(@PathVariable Long cartItemId) 
    {
        return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
    }

    @GetMapping
    public ResponseEntity<CartDto> getCartByUser() 
    {
        return ResponseEntity.ok(cartService.getCartByUser());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CartItemDto>> searchCartItemsByProductName(@RequestParam String productName) 
    {
        return ResponseEntity.ok(cartService.searchCartItemsByProductName(productName));
    }

}
