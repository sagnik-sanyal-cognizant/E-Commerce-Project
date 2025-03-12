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

    //user entity to user DTO

    public UserDto mapUserToDtoBasic(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }

    //Address to DTO Basic
    public AddressDto mapAddressToDtoBasic(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    //Category to DTO basic
    public CategoryDto mapCategoryToDtoBasic(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    //OrderItem to DTO Basics
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

 // Product to DTO Basic
    public ProductDto mapProductToDtoBasic(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDiscount(product.getDiscount());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageData(product.getImageUrl()); // Add this line
        return productDto;
    }

    public CartDto mapCartToDto(Cart cart) 
    {
        CartDto cartDto = new CartDto();

        cartDto.setCartId(cart.getCartId());
        cartDto.setTotalPrice(cart.getTotalPrice());

        if (cart.getCartItemList() != null && !cart.getCartItemList().isEmpty()) 
        {
            List<CartItemDto> cartItemDtoList = cart.getCartItemList().stream()
                        .map(this::mapCartItemToDto)
                        .collect(Collectors.toList());
            cartDto.setCartItemList(cartItemDtoList);
        }

        return cartDto;
    }

    public CartItemDto mapCartItemToDto(CartItem cartItem) 
    {
            CartItemDto cartItemDto = new CartItemDto();

            cartItemDto.setCartItemId(cartItem.getCartItemId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setDiscount(cartItem.getDiscount());
            cartItemDto.setProductPrice(cartItem.getProductPrice());

            if (cartItem.getProduct() != null) 
            {
                cartItemDto.setProduct(mapProductToDtoBasic(cartItem.getProduct()));
            }

            return cartItemDto;
    }

    public UserDto mapUserToDtoPlusAddress(User user){

        System.out.println("mapUserToDtoPlusAddress is called");
        UserDto userDto = mapUserToDtoBasic(user);
        if (user.getAddress() != null){

            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);

        }
        return userDto;
    }


    //orderItem to DTO plus product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);

        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }


    //OrderItem to DTO plus product and user
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);

        if (orderItem.getUser() != null){
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }


    //USer to DTO with Address and Order Items History
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user) {
        UserDto userDto = mapUserToDtoPlusAddress(user);

        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;

    }
}