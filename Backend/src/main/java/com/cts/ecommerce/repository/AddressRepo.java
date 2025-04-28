package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.ecommerce.entity.Address;
import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
