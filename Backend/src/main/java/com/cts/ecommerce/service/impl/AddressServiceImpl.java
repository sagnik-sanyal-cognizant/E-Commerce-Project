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
import com.cts.ecommerce.mapper.EntityDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();
        Optional<Address> existingAddressOptional = addressRepo.findByUserId(user.getId()).stream().findFirst();

        Address address;
        if (existingAddressOptional.isPresent()) {
            address = existingAddressOptional.get();
        } else {
            address = new Address();
            address.setUser(user);
        }

        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        String message = existingAddressOptional.isPresent() ? "Address successfully updated" : "Address successfully created";
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }

    @Override
    public List<AddressDto> getAddressByUserId(Long userId) {
        List<Address> addresses = addressRepo.findByUserId(userId);
        return addresses.stream()
                .map(entityDtoMapper::mapAddressToDtoBasic)
                .collect(Collectors.toList());
    }
}
