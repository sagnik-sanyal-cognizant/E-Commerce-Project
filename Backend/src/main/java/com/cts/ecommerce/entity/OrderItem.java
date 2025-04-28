package com.cts.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.*;

import com.cts.ecommerce.enums.OrderStatus;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product is required")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @NotNull(message = "Order is required")
    private Order order;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;  // Make sure it's the same class or subclass
        OrderItem that = (OrderItem) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(price, that.price) &&
                status == that.status &&
                Objects.equals(user, that.user) &&
                Objects.equals(product, that.product) &&
                Objects.equals(order, that.order);
    }

    // hashCode method ensures the hashCode is equal
    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, status, user, product, order);
    }

    // canEqual method only OrderItem instances can be compared
    public boolean canEqual(Object other) {
        return other instanceof OrderItem;  
    }
}