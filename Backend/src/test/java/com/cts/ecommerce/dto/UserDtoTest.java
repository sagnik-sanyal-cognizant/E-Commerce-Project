package com.cts.ecommerce.dto;

import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

	// Tests the creation and properties of the UserDto
	@Test
    void testUserDto() {

		// Creates an AddressDto with sample data
        AddressDto address = new AddressDto(1L, "Howrah Birdge Road", "Howrah", "West Bengal", "700001", "India", null, null);

        // Creates OrderItemDto objects with sample data
        OrderItemDto orderItem1 = new OrderItemDto(1L, 2, null, "Shipped", null, null, null);
        OrderItemDto orderItem2 = new OrderItemDto(2L, 1, null, "Pending", null, null, null);
        List<OrderItemDto> orderItems = Arrays.asList(orderItem1, orderItem2);

        // Creates a UserDto and assigning values to its fields
        UserDto user = new UserDto();
        user.setId(1L);
        user.setEmail("sagniktesting@email.com");
        user.setName("Sagnik Sanyal");
        user.setPhoneNumber("1234567890");
        user.setPassword("password123");
        user.setRole("USER");
        user.setOrderItemList(orderItems);
        user.setAddress(address);

        // Asserting that the UserDto's fields are correctly set
        assertEquals(1L, user.getId());
        assertEquals("sagniktesting@email.com", user.getEmail());
        assertEquals("Sagnik Sanyal", user.getName());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
        assertNotNull(user.getOrderItemList());
        assertEquals(2, user.getOrderItemList().size());
        assertNotNull(user.getAddress());
        assertEquals("Howrah Birdge Road", user.getAddress().getStreet());

        // Creates another UserDto with the same values for equality and hash code checks
        UserDto user2 = new UserDto(1L, "sagniktesting@email.com", "Sagnik Sanyal", "1234567890", "password123", "USER", orderItems, address);
        assertEquals(user, user2);
        assertEquals(user.hashCode(), user2.hashCode());

        // Asserting that the UserDto's toString method contains specific values
        assertNotNull(user.toString());
        assertTrue(user.toString().contains("sagniktesting@email.com"));
        assertTrue(user.toString().contains("Sagnik Sanyal"));
        assertTrue(user.toString().contains("USER"));
    }
}