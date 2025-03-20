package com.cts.ecommerce.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.UserService;
import com.cts.ecommerce.entity.Cart;
import com.cts.ecommerce.entity.CartItem;
import com.cts.ecommerce.entity.Product;
import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.exception.NotFoundException;
import com.cts.ecommerce.mapper.EntityDtoMapper;
import com.cts.ecommerce.repository.CartItemRepo;
import com.cts.ecommerce.repository.CartRepo;
import com.cts.ecommerce.repository.ProductRepo;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

        //to mock bean use @InjectMocks for that class and @Mock for autowired bean
        @InjectMocks
        private CartServiceImpl cartServiceImpl;

        @Mock
        private CartRepo cartRepo;

        @Mock
        private CartItemRepo cartItemRepo;

        @Mock
        private ProductRepo productRepo;

        @Mock
        private UserService userService;

        @Mock
        private EntityDtoMapper entityDtoMapper; // Mock entityDtoMapper

        //To create mock obj-null values
        Product product=mock(Product.class);
        User user=mock(User.class);
        Cart newCart=mock(Cart.class);
        Cart cart=mock(Cart.class);
        CartItem newCartItem=mock(CartItem.class);

    //stubbing each methods
    @Test
    public void addToCartTest() {     
        long  productId=1L;
        int quantity=1;
        String name="";
        long id=0L;
        BigDecimal price=new BigDecimal("2500");
        List<CartItem> cartItems=new ArrayList<>();

        cartItems.add(newCartItem);
        when(userService.getLoginUser()).thenReturn(user);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(product.getQuantity()).thenReturn(quantity);
        when(product.getName()).thenReturn(name);
        when(cartRepo.findByUserId(id)).thenReturn(Optional.of(cart));
        when(user.getId()).thenReturn(id);
        when(newCartItem.getProduct()).thenReturn(product);
        when(product.getId()).thenReturn(2L);

        when(cart.getCartItemList()).thenReturn(cartItems);
        when(product.getPrice()).thenReturn(price);
        when(cartItemRepo.save(any())).thenReturn(newCartItem);

        Response expected=Response.builder()
                        .status(200)
                        .message("Product added to cart ")
                        .build();
        
        assertEquals(expected.getMessage(),cartServiceImpl.addToCart(productId).getMessage());

        verify(userService,times(1)).getLoginUser();
        verify(productRepo,times(1)).findById(productId);
        verify(product,times(1)).getQuantity();
        verify(cartRepo,times(1)).findByUserId(id);
        verify(user,times(1)).getId();
    }

    @Test
    public void addToItemExistingCartTest() {     
        long  productId=1L;
        int quantity=1;
        String name="";
        long id=0L;
        BigDecimal price=new BigDecimal("2500");
        List<CartItem> cartItems=new ArrayList<>();

        cartItems.add(newCartItem);
        when(userService.getLoginUser()).thenReturn(user);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(product.getQuantity()).thenReturn(quantity);
        when(product.getName()).thenReturn(name);
        when(cartRepo.findByUserId(id)).thenReturn(Optional.of(cart));
        when(user.getId()).thenReturn(id);
        when(newCartItem.getProduct()).thenReturn(product);
        when(product.getId()).thenReturn(1L);

        when(cart.getCartItemList()).thenReturn(cartItems);
        when(product.getPrice()).thenReturn(price);

        Response expected=Response.builder()
                        .status(200)
                        .message("Item already added to the cart. If needed, please update it.")
                        .build();
        
        assertEquals(expected.getMessage(),cartServiceImpl.addToCart(productId).getMessage());
        
        verify(userService,times(1)).getLoginUser();
        verify(productRepo,times(1)).findById(productId);
        verify(product,times(1)).getQuantity();
        verify(cartRepo,times(1)).findByUserId(id);
        verify(user,times(1)).getId();
    }

        @Test
        public void updateCartItemByQuantityTest() {

        // Initialize test data
        Long cartItemId = 1L;
        Long productId = 1L;

        // Mock objects and stubbing logic (same as described in earlier test case)
        User user = mock(User.class);
        CartItem cartItem = mock(CartItem.class);
        Product product = mock(Product.class);
        Cart cart = mock(Cart.class);

        when(userService.getLoginUser()).thenReturn(user);
        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        when(cartItem.getCart()).thenReturn(cart);
        when(cart.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(cartItem.getProduct()).thenReturn(product);
        when(product.getId()).thenReturn(productId);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(product.getQuantity()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal("100.00"));
        when(product.getDiscount()).thenReturn(10.0);
        when(cartItem.getQuantity()).thenReturn(1);

        // Perform the test
        Response response = cartServiceImpl.updateCartItemByQuantity(cartItemId, productId);

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("Cart item updated successfully", response.getMessage());

        // Verify interactions
        verify(cartItemRepo, times(1)).findById(cartItemId);
        verify(productRepo, times(1)).findById(productId);
        verify(cartItemRepo, times(1)).save(cartItem);
        verify(userService, times(1)).getLoginUser();
        }   
        
        @Test
        public void removeCartTest() {

            // Arrange
            Long cartItemId = 1L;
            CartItem cartItem = new CartItem();
            Cart cart = new Cart();
        
            // Set up relationships
            cartItem.setCart(cart);
            cart.setCartItemList(new ArrayList<>(Arrays.asList(cartItem))); // Use Arrays.asList() for compatibility
        
            // Mock repository behavior
            when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        
            // Act
            Response response = cartServiceImpl.removeCartItem(cartItemId);
        
            // Assert
            assertEquals(200, response.getStatus());
            assertEquals("Cart item removed", response.getMessage());
            verify(cartItemRepo).delete(cartItem); // Verify delete method called
            assertTrue(cart.getCartItemList().isEmpty()); // Verify cart item removed from list
        }
         
        @Test
        void testRemoveCartItem_NotFound() {
            // Arrange
            Long cartItemId = 1L;
     
            when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.empty());
     
            // Act & Assert
            Exception exception = assertThrows(NotFoundException.class, () -> {
                cartServiceImpl.removeCartItem(cartItemId);
            });
            assertEquals("Cart item not found", exception.getMessage());
        }
}
      