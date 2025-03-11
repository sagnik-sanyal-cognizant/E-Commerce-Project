package com.cts.ecommerce.service.interf;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}