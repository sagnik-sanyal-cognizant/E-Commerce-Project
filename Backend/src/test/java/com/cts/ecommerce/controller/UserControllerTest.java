package com.cts.ecommerce.controller;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.service.interf.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;

	// Injecting Mock Class of real class for testing purpose
    
    @Mock
    private UserService userService;

    // Sets up the test environment before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    // Tests the successful retrieval of all users
    @Test
    void testGetAllUsers_Success() {

    	// Arrange: Sets up the mock response and behavior for the userService
        Response mockResponse = Response.builder()
                .status(200)
                .message("All users retrieved successfully")
                .build();
        when(userService.getAllUsers()).thenReturn(mockResponse);

        // Act - Calls the getAllUsers method of the userController
        ResponseEntity<Response> response = userController.getAllUsers();

        // Assert - Verifies the response and interactions with the userService
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All users retrieved successfully", response.getBody().getMessage());
        verify(userService, times(1)).getAllUsers();
    }

    // Tests the failure scenario when retrieving all users
    @Test
    void testGetAllUsers_Failure() {
    	// Arrange - Sets up the mock behavior for the userService to throw an exception
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Failed to retrieve users"));

        // Act & Assert - Calls the getAllUsers method and verifies the exception
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.getAllUsers()
        );
        assertEquals("Failed to retrieve users", exception.getMessage());
        verify(userService, times(1)).getAllUsers();
    }
    
    // Tests the successful retrieval of user info and order history
    @Test
    void testGetUserInfoAndOrderHistory_Success() {
    	// Arrange - Sets up the mock response and behavior for the userService
        Response mockResponse = Response.builder()
                .status(200)
                .message("User info and order history retrieved successfully")
                .build();
        when(userService.getUserInfoAndOrderHistory()).thenReturn(mockResponse);

        // Act - Calls the getUserInfoAndOrderHistory method of the userController
        ResponseEntity<Response> response = userController.getUserInfoAndOrderHistory();

        // Assert - Verifies the response and interactions with the userService
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User info and order history retrieved successfully", response.getBody().getMessage());
        verify(userService, times(1)).getUserInfoAndOrderHistory();
    }

    // Tests the failure scenario when retrieving user info and order history
    @Test
    void testGetUserInfoAndOrderHistory_Failure() {
    	// Arrange - Sets up the mock behavior for the userService to throw an exception
        when(userService.getUserInfoAndOrderHistory()).thenThrow(new RuntimeException("Failed to retrieve user info"));

        // Act & Assert - Calls the getUserInfoAndOrderHistory method and verifies the exception
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.getUserInfoAndOrderHistory()
        );
        assertEquals("Failed to retrieve user info", exception.getMessage());
        verify(userService, times(1)).getUserInfoAndOrderHistory();
    }
}