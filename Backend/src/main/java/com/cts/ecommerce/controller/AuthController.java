package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecommerce.dto.LoginRequest;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.dto.UserDto;
import com.cts.ecommerce.service.interf.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	// UserService is injected to handle the business logic for user operations
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody UserDto registrationRequest) {
        System.out.println(registrationRequest); // Logging..
        
        // Calls the user service to register the user and returns the response
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        
    	// Calls the user service to login the user and returns the response
    	return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}