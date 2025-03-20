package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

	// AddressService is injected to handle the business logic for address operations
    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAndUpdateAddress(@Valid @RequestBody AddressDto addressDto){
    	
    	// Calls the address service to save or update the address and returns the response
    	return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto));
    }
}