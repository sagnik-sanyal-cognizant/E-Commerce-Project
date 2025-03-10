package com.cts.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.Ecommerce.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
