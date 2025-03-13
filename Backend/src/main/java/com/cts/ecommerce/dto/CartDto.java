package com.cts.ecommerce.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long cartId;
    private BigDecimal totalPrice;
    private List<CartItemDto> cartItemList;
}
