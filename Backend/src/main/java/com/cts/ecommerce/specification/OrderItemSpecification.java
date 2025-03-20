package com.cts.ecommerce.specification;

import org.springframework.data.jpa.domain.Specification;

import com.cts.ecommerce.entity.OrderItem;
import com.cts.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;

// Provides specifications for filtering OrderItem entities based on various criteria.
public class OrderItemSpecification {

    // Specification to filter order items by status if valid or null 
    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status) : null);

    }

    // Specification to filter order items by data range null, greater, lesser to the End Date
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate){
        return ((root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null){
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }else{
                return null;
            }
        });
    }

    // Generate specification to filter OrderItems by item id if valid or null
    public static Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null);
    }
}
