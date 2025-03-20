package com.cts.ecommerce.entity;

import org.junit.jupiter.api.Test;

import com.cts.ecommerce.enums.UserRole;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	// Tests the creation of a User entity and verifies its properties
    @Test
    public void testUserCreation() {
    	
    	// Creates a User entity using the builder pattern for test data inputs
        User user = User.builder()
                .name("Sagnik Sanyal")
                .email("sagniksanyal@email.com")
                .password("password123")
                .phoneNumber("1234567890")
                .role(UserRole.ADMIN)
                .build();

        // Asserting that the given data are set to User entity properly
        assertNotNull(user);
        assertEquals("Sagnik Sanyal", user.getName());
        assertEquals("sagniksanyal@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals(UserRole.ADMIN, user.getRole());
    }
}