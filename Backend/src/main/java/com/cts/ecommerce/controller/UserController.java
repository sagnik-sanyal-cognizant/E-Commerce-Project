package com.cts.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	// UserService is injected to handle the business logic for user operations
    private final UserService userService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
    	
    	// Calls the user service to retrieve all users and returns the response
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/my-info")
    public ResponseEntity<Response> getUserInfoAndOrderHistory(){
    	
    	// Calls the user service to retrieve the current user's information and order history and returns the response
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
    }
}