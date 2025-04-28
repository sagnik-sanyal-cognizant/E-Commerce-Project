package com.cts.ecommerce.service.interf;

import java.util.List;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
    List<AddressDto> getAddressByUserId(Long userId);
}