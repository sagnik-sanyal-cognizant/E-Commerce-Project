package com.cts.ecommerce.entity;

import org.junit.jupiter.api.Test;

import com.cts.ecommerce.enums.UserRole;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = User.builder()
                .name("Sagnik Sanyal")
                .email("sagniksanyal@email.com")
                .password("password123")
                .phoneNumber("1234567890")
                .role(UserRole.ADMIN)
                .build();

        assertNotNull(user);
        assertEquals("Sagnik Sanyal", user.getName());
        assertEquals("sagniksanyal@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals(UserRole.ADMIN, user.getRole());
    }
}