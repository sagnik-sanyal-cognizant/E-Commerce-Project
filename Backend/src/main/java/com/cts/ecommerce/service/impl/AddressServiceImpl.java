package com.cts.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.entity.Address;
import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.repository.AddressRepo;
import com.cts.ecommerce.service.interf.AddressService;
import com.cts.ecommerce.service.interf.UserService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;

    // Saves or updates an address for the logged-in user
    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        
    	User user = userService.getLoginUser();
        Address address = user.getAddress();

        if (address == null) { // Checks if the user does not have an address and creates a new
            address = new Address();
            address.setUser(user);
        }
        // Updates the address fields if they are provided in the addressDto
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        // Determines the message based on whether the address was created or updated
        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }
}
