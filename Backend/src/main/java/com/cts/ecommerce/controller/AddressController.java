package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.ecommerce.dto.AddressDto;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAndUpdateAddress(@Valid @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDto>> getAddressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressByUserId(userId));
    }

}
