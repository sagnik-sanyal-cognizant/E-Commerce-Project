package com.cts.Ecommerce.service.interf;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

import com.cts.Ecommerce.dto.OrderRequest;
import com.cts.Ecommerce.dto.Response;
import com.cts.Ecommerce.enums.OrderStatus;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}