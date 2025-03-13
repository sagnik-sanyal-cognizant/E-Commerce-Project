package com.cts.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long cartItemId;
    private ProductDto product;
    private Integer quantity;
    private double discount;
    private double productPrice;
}
