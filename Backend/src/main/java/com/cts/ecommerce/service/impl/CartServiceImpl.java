package com.cts.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ecommerce.dto.CartDto;
import com.cts.ecommerce.dto.CartItemDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.entity.Cart;
import com.cts.ecommerce.entity.CartItem;
import com.cts.ecommerce.entity.Product;
import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.exception.NotFoundException;
import com.cts.ecommerce.exception.UnauthorizedException;
import com.cts.ecommerce.mapper.EntityDtoMapper;
import com.cts.ecommerce.repository.CartItemRepo;
import com.cts.ecommerce.repository.CartRepo;
import com.cts.ecommerce.repository.ProductRepo;
import com.cts.ecommerce.service.interf.CartService;
import com.cts.ecommerce.service.interf.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService 
{

    @Autowired
    private  CartRepo cartRepo;

    @Autowired
    private  CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityDtoMapper entityDtoMapper;

        @Override
        public Response addToCart(Long productId) {
                User user = userService.getLoginUser();

                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new NotFoundException("Product not found"));
                
                if(product.getQuantity()<=0) {
                        throw new NotFoundException("Product "+product.getName() +"is not available");
                }

                // Retrieve the user's cart or create a new one if it doesn't exist
                Cart cart = cartRepo.findByUserId(user.getId()).orElseGet(() -> {
                        Cart newCart = new Cart();

                        newCart.setUser(user);
                        newCart.setTotalPrice(BigDecimal.ZERO);
                        newCart.setCartItemList(new ArrayList<>()); 
                        
                        return cartRepo.save(newCart);
                });
        
                // Check if the product already exists in the cart
                CartItem existingCartItem = cart.getCartItemList().stream()
                        .filter(item -> item.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElse(null);
                
                if (existingCartItem != null) {
                        return Response.builder()
                        .status(200)
                        .message("Item already added to the cart. If needed, please update it.")
                        .build(); 
                } else {
                
                	CartItem newCartItem = new CartItem(); // Add the product as a new cart item
                
                	newCartItem.setCart(cart);
                	newCartItem.setProduct(product);
                	newCartItem.setQuantity(1);
	                newCartItem.setDiscount(product.getDiscount());
	                newCartItem.setProductPrice (
                        product.getPrice().doubleValue() * (1 - product.getDiscount() / 100)
	                );
                
	                cartItemRepo.save(newCartItem);
	                cart.getCartItemList().add(newCartItem);
                }
        
                String res=updateCartTotalPrice(cart); // Update the cart's total price
        
                return Response.builder()
                        .status(200)
                        .message("Product added to cart "+res)
                        .build();
        }
    
        public Response updateCartItemByQuantity(Long cartItemId, Long productId) {
                User user = userService.getLoginUser();

                // Fetch the CartItem and validate it belongs to the user
                CartItem cartItem = cartItemRepo.findById(cartItemId)
                        .orElseThrow(() -> new NotFoundException("Cart item not found"));
        
                // Ensure the productId matches the cartItem's product and belongs to the logged-in user

                if (!cartItem.getCart().getUser().getId().equals(user.getId()) || 
                !cartItem.getProduct().getId().equals(productId)) {
                        throw new UnauthorizedException("Invalid cart item or product for the logged-in user");
                }
        
                // Fetch the associated product
                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new NotFoundException("Product not found"));
                
                if(product.getQuantity()<=0) {
                        throw new NotFoundException("Product "+product.getName() +"is not available");
                }

                // Increment the quantity and recalculate price
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setDiscount(product.getDiscount());
                cartItem.setProductPrice (
                        product.getPrice().doubleValue() * cartItem.getQuantity() * (1 - cartItem.getDiscount() / 100)
                );
                System.out.println("inside update service");
                
                // Update the cart's total price
                updateCartTotalPrice(cartItem.getCart());
                
                // Save the updated CartItem
                cartItemRepo.save(cartItem);

                return Response.builder()
                        .status(200)
                        .message("Cart item updated successfully")
                        .build();
        }
    
        @Override
        public Response removeCartItemByQuantity(Long cartItemId,Long productId) {
                User user = userService.getLoginUser();
                
                // Fetch the CartItem and validate it belongs to the user
                CartItem cartItem = cartItemRepo.findById(cartItemId)
                        .orElseThrow(() -> new NotFoundException("Cart item not found"));

                if(cartItem.getQuantity()==1) {
                        Cart cart=cartItem.getCart();
                        cart.getCartItemList().remove(cartItem);
                        
                        updateCartTotalPrice(cart);
                       
                        cartItemRepo.deleteById(cartItemId);
                        
                        return Response.builder()
                        .status(200)
                        .message("Cart item removed successfully")
                        .build();
                }
        
                // Ensure the productId matches the cartItem's product and belongs to the logged-in user
                if (!cartItem.getCart().getUser().getId().equals(user.getId()) || 
                !cartItem.getProduct().getId().equals(productId)) {
                        throw new UnauthorizedException("Invalid cart item or product for the logged-in user");
                }
        
                // Fetch the associated product
                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new NotFoundException("Product not found"));
                
                if(cartItem.getQuantity()<=0) {
                        throw new NotFoundException("CartItem is removed from the cart");
                }

                // Increment the quantity and recalculate price
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItem.setDiscount(product.getDiscount());
                cartItem.setProductPrice (
                        product.getPrice().doubleValue() * cartItem.getQuantity() * (1 - cartItem.getDiscount() / 100)
                );
                
                Cart cart=cartItem.getCart();
                int index=cart.getCartItemList().indexOf(cartItem);
                cart.getCartItemList().set(index,cartItem);
                
                // Update the cart's total price
                updateCartTotalPrice(cart);
                
                // Save the updated CartItem
                cartItemRepo.save(cartItem);
                
                return Response.builder()
                        .status(200)
                        .message("Cart item removed successfully")
                        .build();
        }

        @Override
        public Response removeCartItem(Long cartItemId) {
                CartItem cartItem = cartItemRepo.findById(cartItemId)
                        .orElseThrow(() -> new NotFoundException("Cart item not found"));

                Cart cart = cartItem.getCart();
                cartItemRepo.delete(cartItem);
                cart.getCartItemList().remove(cartItem);
                
                updateCartTotalPrice(cart);

                return Response.builder()
                        .status(200)
                        .message("Cart item removed")
                        .build();
        }

        @Override
        public CartDto getCartByUser(Long userId) {
                User user = userService.getLoginUser();

                Cart cart = cartRepo.findByUserId(user.getId())
                        .orElseThrow(() -> new NotFoundException("Cart not found"));

                return entityDtoMapper.mapCartToDto(cart);
        }

        @Override
        public List<CartItemDto> searchCartItemsByProductName(String productName) {
                User user = userService.getLoginUser();
               
                List<CartItem> cartItems = cartItemRepo.findCartItemsByProductName(user.getId(), productName);

                if (cartItems.isEmpty()) {
                        throw new NotFoundException("No products found in the cart with the name: " + productName);
                }

                return cartItems.stream()
                        .map(entityDtoMapper::mapCartItemToDto)
                        .collect(Collectors.toList());
        }

        private String updateCartTotalPrice(Cart cart) {
                BigDecimal totalPrice = BigDecimal.ZERO;

                StringBuilder logDetails = new StringBuilder();

                for (CartItem cartItem : cart.getCartItemList()) {
                
                        if (cartItem != null && cartItem.getProductPrice() != 0) {
                                double productPrice = cartItem.getProductPrice();
                                logDetails.append(" Print product price: ").append(productPrice).append(" ");
                                totalPrice = totalPrice.add(BigDecimal.valueOf(productPrice));
                        }
                }
        
                cart.setTotalPrice(totalPrice);
                cartRepo.save(cart);
        
                return logDetails.toString();
        }
        
}
