package com.cts.ecommerce.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.dto.UserDto;
import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.enums.UserRole;
import com.cts.ecommerce.mapper.EntityDtoMapper;
import com.cts.ecommerce.repository.UserRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EntityDtoMapper entityDtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        UserDto registrationRequest = new UserDto();
        registrationRequest.setName("Sagnik Sanyal");
        registrationRequest.setEmail("sagniktesting@email.com");
        registrationRequest.setPassword("password123");
        registrationRequest.setPhoneNumber("1234567890");
        registrationRequest.setRole("USER");

        User user = User.builder()
                .name("Sagnik Sanyal")
                .email("sagniktesting@email.com")
                .password("hashedPassword")
                .phoneNumber("1234567890")
                .role(UserRole.USER)
                .build();

        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(entityDtoMapper.mapUserToDtoBasic(user)).thenReturn(registrationRequest);

        // Collecting response
        Response response = userService.registerUser(registrationRequest);

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("User Successfully Added", response.getMessage());
        verify(userRepo, times(1)).save(any(User.class));
    }
}