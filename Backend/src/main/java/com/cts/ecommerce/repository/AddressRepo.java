package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}