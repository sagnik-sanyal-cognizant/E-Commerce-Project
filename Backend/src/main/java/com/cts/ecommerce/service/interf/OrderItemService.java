package com.cts.ecommerce.service.interf;

import org.springframework.data.domain.Pageable;

import com.cts.ecommerce.dto.OrderRequest;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}