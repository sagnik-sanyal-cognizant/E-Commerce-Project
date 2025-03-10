package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
