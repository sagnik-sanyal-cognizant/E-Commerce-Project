package com.cts.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.CartDto;
import com.cts.ecommerce.dto.CartItemDto;
import com.cts.ecommerce.dto.CategoryDto;
import com.cts.ecommerce.dto.OrderItemDto;
import com.cts.ecommerce.dto.ProductDto;
import com.cts.ecommerce.dto.UserDto;
import com.cts.ecommerce.entity.Address;
import com.cts.ecommerce.entity.Cart;
import com.cts.ecommerce.entity.CartItem;
import com.cts.ecommerce.entity.Category;
import com.cts.ecommerce.entity.OrderItem;
import com.cts.ecommerce.entity.Product;
import com.cts.ecommerce.entity.User;

@Component
public class EntityDtoMapper {

    // Maps a User entity to a UserDto with basic details
    public UserDto mapUserToDtoBasic(User user) {
    	
        UserDto userDto = new UserDto();
        
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }

    // Maps an Address entity to an AddressDto with basic details
    public AddressDto mapAddressToDtoBasic(Address address) {
    	
        AddressDto addressDto = new AddressDto();
        
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    // Maps a Category entity to a CategoryDto with basic details
    public CategoryDto mapCategoryToDtoBasic(Category category) {
    	
        CategoryDto categoryDto = new CategoryDto();
        
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    // Maps an OrderItem entity to an OrderItemDto with basic details
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem) {
    	
        OrderItemDto orderItemDto = new OrderItemDto();
        
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    // Maps a Product entity to a ProductDto with basic details
    public ProductDto mapProductToDtoBasic(Product product) {
    	
        ProductDto productDto = new ProductDto();
        
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDiscount(product.getDiscount());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageData(product.getImageUrl());
        return productDto;
    }
    
 // Maps a Cart entity to a CartDto
    public CartDto mapCartToDto(Cart cart) {
        CartDto cartDto = new CartDto();

        cartDto.setCartId(cart.getCartId());
        cartDto.setTotalPrice(cart.getTotalPrice());

        // Checks if the cart has any cart items and maps them to CartItemDto
        if (cart.getCartItemList() != null && !cart.getCartItemList().isEmpty()) {
            List<CartItemDto> cartItemDtoList = cart.getCartItemList().stream()
                        .map(this::mapCartItemToDto)
                        .collect(Collectors.toList());
            cartDto.setCartItemList(cartItemDtoList);
        }
        return cartDto;
    }

    // Maps a CartItem entity to a CartItemDto
    public CartItemDto mapCartItemToDto(CartItem cartItem) {
            CartItemDto cartItemDto = new CartItemDto();

            cartItemDto.setCartItemId(cartItem.getCartItemId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setDiscount(cartItem.getDiscount());
            cartItemDto.setProductPrice(cartItem.getProductPrice());

            // Checks if the cart item has an associated product and maps it to ProductDto
            if (cartItem.getProduct() != null) {
                cartItemDto.setProduct(mapProductToDtoBasic(cartItem.getProduct()));
            }
            return cartItemDto;
    }

    // Maps a User entity to a UserDto with address details
    public UserDto mapUserToDtoPlusAddress(User user) {

        System.out.println("mapUserToDtoPlusAddress is called"); // Logging...
        
        UserDto userDto = mapUserToDtoBasic(user);
        
        // Checks if the user has an associated address and maps it to AddressDto
        if (user.getAddress() != null){
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }


    // Maps an OrderItem entity to an OrderItemDto with product details
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);

        // Checks if the order item has an associated product and maps it to ProductDto
        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }


    // Maps an OrderItem entity to an OrderItemDto with product and user details
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);

        // Checks if the order item has an associated user and maps it to UserDto
        if (orderItem.getUser() != null) {
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }


    // Maps a User entity to a UserDto with address and order history details
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user) {
        UserDto userDto = mapUserToDtoPlusAddress(user);

        // Checks if the user has an order history and maps each order item to OrderItemDto
        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;
    }
    
}