package com.cts.ecommerce.service.interf;


import java.util.List;
import com.cts.ecommerce.dto.CartDto;
import com.cts.ecommerce.dto.CartItemDto;
import com.cts.ecommerce.dto.Response;

public interface CartService 
{
    Response addToCart(Long productId);
    Response updateCartItemByQuantity(Long cartItemId,Long productId);
    Response removeCartItemByQuantity(Long cartItemId,Long productId);
    Response removeCartItem(Long cartItemId);
    CartDto getCartByUser();
    List<CartItemDto> searchCartItemsByProductName(String productName);
}
